package com.example.h2_shop.service.impl;

import com.example.h2_shop.commons.ReflectorUtil;
import com.example.h2_shop.model.*;
import com.example.h2_shop.model.dto.*;
import com.example.h2_shop.model.mapper.OrderDetailMapper;
import com.example.h2_shop.model.mapper.OrderMapper;
import com.example.h2_shop.model.mapper.ProductImgMapper;
import com.example.h2_shop.repository.*;
import com.example.h2_shop.service.FileService;
import com.example.h2_shop.service.OrderService;
import com.example.h2_shop.service.ServiceResult;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final ApparamsRepository apparamsRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final SaleRepository saleRepository;
    private final ProductDetailRepository productDetailRepository;
    private final FileService fileService;
    private final ProductImgRepository productImgRepository;
    private final ProductImgMapper productImgMapper;

    public OrderServiceImpl(FileService fileService,ProductImgMapper productImgMapper, ProductImgRepository productImgRepository, UserRepository userRepository,ProductDetailRepository productDetailRepository,OrderDetailRepository orderDetailRepository, OrderDetailMapper orderDetailMapper,SaleRepository saleRepository,OrderRepository orderRepository,OrderMapper orderMapper, ProductRepository productRepository,ApparamsRepository apparamsRepository) {
        this.productRepository = productRepository;
        this.apparamsRepository=apparamsRepository;
        this.userRepository=userRepository;
        this.orderMapper=orderMapper;
        this.orderRepository = orderRepository;
        this.saleRepository=saleRepository;
        this.orderDetailMapper=orderDetailMapper;
        this.orderDetailRepository=orderDetailRepository;
        this.productDetailRepository = productDetailRepository;
        this.fileService=fileService;
        this.productImgRepository=productImgRepository;
        this.productImgMapper = productImgMapper;
    }

    @Override
    public ServiceResult<OrdersDTO> createOrder(OrdersDTO ordersDTO) {
        String err = this.validationOrder(ordersDTO);
        ServiceResult<OrdersDTO> serviceResult = new ServiceResult<>();
        if(!err.isEmpty()){
            serviceResult.setData(null);
            serviceResult.setMessage(err);
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            return serviceResult;
        }else{
            ordersDTO.setOrderDate(Instant.now());
            Optional<User> user = this.userRepository.findById(ordersDTO.getUserId());
            ordersDTO.setUser(user.get());

            ordersDTO.setStatus(0L);
            LocalDateTime currentTime = LocalDateTime.now();
            ordersDTO.setOrderCode("ORDER_"+currentTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

            Orders orders = this.orderMapper.toEntity(ordersDTO);
            orders = this.orderRepository.save(orders); // lưu cuối cùng

            float totalPriceOrder = 0;
            List<OrderDetailDTO> orderDetailDTOS = ordersDTO.getOrderDetailDTOList();
            List<ProductDetail> productDetailList = new ArrayList<>(); // danh sách dùng để cập nhật lại số lượng sau khi đã order
            Float tax = 0F;
            for(int i=0;i<orderDetailDTOS.size();i++){
                orderDetailDTOS.get(i).setOrders(orders);
                Optional<ProductDetail> productDTOP = this.productDetailRepository.findByTypeIdSizeIdProductId(orderDetailDTOS.get(i).getTypeProductId(), orderDetailDTOS.get(i).getSizeProductId(), orderDetailDTOS.get(i).getProductId());

                if(productDTOP.isPresent()){
                    Optional<Sale> saleOp = this.saleRepository.findByProductID(productDTOP.get().getProduct().getId());
                    Product product = this.productRepository.findById(productDTOP.get().getProduct().getId()).get(); // chắc chắn tồn tại id sản phẩm rồi

                    ProductDetail productDetail = productDTOP.get();

                    // đổi nghiệp vụ => khi phê duyệt đơn hàng thì mới giảm số lượng sản phẩm
                          // productDetail.setQuantity( productDetail.getQuantity() - orderDetailDTOS.get(i).getQuantity());
                          //  productDetailList.add(productDetail); // thêm sản phẩm để cập nhật số lượng sau khi mua

                    orderDetailDTOS.get(i).setProductDetail(productDetail);

                    if(saleOp.isPresent()){// lấy giá sản phẩm sau khi đã discount sau khi sản phẩm giảm giá
                        orderDetailDTOS.get(i).setPrice(product.getPrice()*((100-(saleOp.get().getMaxPurchase()))/100));
                        totalPriceOrder += product.getPrice()*((100-(saleOp.get().getMaxPurchase()))/100)*orderDetailDTOS.get(i).getQuantity();
                    }else{
                        totalPriceOrder+=product.getPrice()*orderDetailDTOS.get(i).getQuantity();
                    }

                    if(product.getTax()!=null){
                        tax+= product.getPrice()*orderDetailDTOS.get(i).getQuantity() * (product.getTax()/100);
                    }
                    orderDetailDTOS.get(i).setPrice(Float.parseFloat(product.getPrice()+""));
                }

            }
            List<OrderDetail> orderDetailList = this.orderDetailMapper.toEntity(orderDetailDTOS);
            orderDetailList = this.orderDetailRepository.saveAll(orderDetailList); // lưu danh sách orderdetail
            orderDetailDTOS = this.orderDetailMapper.toDto(orderDetailList);


            if(ordersDTO.getSaleId()!=null){ // validate mã giảm giá cho hóa đơn

                Optional<Sale> sale = this.saleRepository.findById(ordersDTO.getSaleId());
                String errSale = "";
                if(sale.isPresent()){
                    Sale sale1 = sale.get();
                    orders.setSale(sale1);
                    float priceDiscount = totalPriceOrder * (sale1.getMaxPurchase()/100); // số tiền giảm giá
                    Instant timeNow = Instant.now();
                    if(timeNow.isAfter(sale1.getStartTime()) && timeNow.isBefore(sale1.getEndTime()) && sale1.getQuantity()>0){
                        sale1.setQuantity(sale1.getQuantity()-1);
                        this.saleRepository.save(sale1);
//                        if(totalPriceOrder < sale1.getMinPrice()){ // nếu có điều kiện cho giá hóa đơn thấp nhất
//                            errSale = "Tổng giá của hóa đơn không phù hợp để áp dụng";
//                        }
//
//                        if(sale1.getMaxDiscount()!=null){ // nếu mã giảm giá giới hạn số tiền giảm giá
//                            if (priceDiscount > sale1.getMaxDiscount()){
//                                priceDiscount = totalPriceOrder - sale1.getMaxDiscount();
//                            }
//                        }
                    }else{
                        errSale = "Mã giảm giá đã quá hạn hoặc không còn đủ số lượng";
                    }
                    totalPriceOrder = totalPriceOrder - priceDiscount;

                    if(sale1.getType() == "1"){
                        errSale = "Mã giảm giá không đúng";
                    }

                    if(!errSale.isEmpty()){
                        serviceResult.setMessage(errSale);
                        serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                        serviceResult.setData(null);
                        return serviceResult;
                    }

                }


            }
            if(ordersDTO.getShipPrice()!=0){
                totalPriceOrder = totalPriceOrder + orders.getShipPrice();
                orders.setShipPrice(ordersDTO.getShipPrice());
            }

            orders.setPrice(totalPriceOrder);//+tax
            this.orderRepository.save(orders);
//            orders = this.orderRepository.save(orders);


//            productDetailList = this.productDetailRepository.saveAll(productDetailList); // đổi nghiệp vụ giảm số lượng sản phẩm khi đặt hàng
            ordersDTO = this.orderMapper.toDto(orders);
            ordersDTO.setOrderDetailDTOList(orderDetailDTOS);
            serviceResult.setData(ordersDTO);
            serviceResult.setStatus(HttpStatus.OK);
            serviceResult.setMessage("Lưu thành công");

            return  serviceResult;
        }
    }

    @Override
    public ServiceResult<OrdersDTO> getAllOrder(Long userId) {



        return null;
    }

    @Override
    public ServiceResult<Page<OrderRequestDTO>> searchOrder(OrderRequestDTO orderRequestDTO) {
        Pageable pageable = PageRequest.of(orderRequestDTO.getPage()-1,orderRequestDTO.getPageSize());
        Page<Map<String,Object>> page = this.orderRepository.searchOrder(pageable, orderRequestDTO.getStatus(),orderRequestDTO.getKeySearch());

        List<Map<String,Object>> map = page.getContent();
        List<OrderRequestDTO> lst = map.stream().map(item -> ReflectorUtil.mapToDTO(item , OrderRequestDTO.class)).collect(Collectors.toList());

        Page<OrderRequestDTO> pageReturn = new PageImpl<>(lst, pageable,page.getTotalElements());

        return new ServiceResult<>(pageReturn,HttpStatus.OK,"");
    }

    @Override
    public ServiceResult<OrderViewDetailDTO> detailOrder(Long id) {

        Map<String,Object> map = this.orderRepository.detailOrder(id);

        OrderViewDetailDTO orderViewDetailDTO = ReflectorUtil.mapToDTO(map,OrderViewDetailDTO.class);

        List<Map<String,Object>> odMap = this.orderRepository.getListOrderDetailOfOrder(orderViewDetailDTO.getOrderDetailConcat());
        List<OrderDetailForCartDTO> orderDetailForCartDTOS = odMap.stream().map(item -> ReflectorUtil.mapToDTO(item, OrderDetailForCartDTO.class)).collect(Collectors.toList());

        orderViewDetailDTO.setOrderDetailForCartDTOS(orderDetailForCartDTOS);

        return new ServiceResult<>(orderViewDetailDTO,HttpStatus.OK,"");
    }

    @Override
    public ServiceResult<CommentDTO> createComment(List<MultipartFile> filesComment, CommentDTO commentDTO) {

        String err = this.validatorComment(filesComment,commentDTO);
        ServiceResult<CommentDTO> serviceResult = new ServiceResult<>();
        if(err.length()>0){
            serviceResult.setMessage(err);
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            return serviceResult;
        }else{
            CommentDTO commentDTOReturn = new CommentDTO();

            OrderDetail orderDetail = this.orderDetailRepository.findListOrderCompleteByProductAndUser(commentDTO.getUserID(), commentDTO.getProductID(), commentDTO.getOrderDetailID());

            orderDetail.setComment(commentDTO.getComment());
            if(commentDTO.getRating()!=null){
                orderDetail.setRating(commentDTO.getRating());
            }
            List<ProductImg> productImgList = new ArrayList<>();
            if(filesComment!=null){
                ServiceResult<List<FileDto>> serviceListFile = this.fileService.createListFile(filesComment);
                if(serviceListFile.getStatus()==HttpStatus.OK){

                    List<FileDto> fileDtos= serviceListFile.getData();
                    for (int i=0;i<fileDtos.size();i++){
                        ProductImg productImg = new ProductImg();
                        productImg.setFileSize(fileDtos.get(i).getFileSize()+ "bytes");
                        productImg.setOrderDetail(orderDetail);
                        productImg.setFileName(fileDtos.get(i).getFileName());
                        productImg.setFileId(fileDtos.get(i).getFileId());
                        productImg.setType("IMGCMT");
                        productImgList.add(productImg);
                    }
                    this.productImgRepository.saveAll(productImgList);
                }
            }
            orderDetail = this.orderDetailRepository.save(orderDetail);
            OrderDetailDTO orderDetailDTO = this.orderDetailMapper.toDto(orderDetail);
            orderDetailDTO.setTypeProductId(orderDetail.getProductDetail().getTypeProduct().getId());
            orderDetailDTO.setSizeProductId(orderDetail.getProductDetail().getSize().getId());
            orderDetailDTO.setProductDetail(null);
            orderDetailDTO.setOrders(null);

            commentDTOReturn.setOrderDetailDTO(orderDetailDTO);

            List<ProductImgDTO> productImgDTO = this.productImgMapper.toDto(productImgList);
            commentDTOReturn.setProductImgDTOList(productImgDTO);

            serviceResult.setStatus(HttpStatus.OK);
            serviceResult.setMessage("Bạn đã bình luận thành công");
            serviceResult.setData(commentDTOReturn);
            return serviceResult;

        }
    }

    @Override
    public ServiceResult<?> updateOrder(OrderViewDetailDTO orderViewDetailDTO) {

        List<Map<String,Object>> map = this.orderRepository.getListOrderDetailOfOrder(orderViewDetailDTO.getOrderDetailConcat());



        List<OrderDetailForCartDTO> orderDetailForCartDTOS =map.stream().map(item->ReflectorUtil.mapToDTO(item,OrderDetailForCartDTO.class)).collect(Collectors.toList());
//        List<OrderDetailForCartDTO> orderDetailForCartDTOS = orderViewDetailDTO.getOrderDetailForCartDTOS();


        String err = "";
        List<Long> productDetailIdLst=new ArrayList<>();
        for(OrderDetailForCartDTO item :orderDetailForCartDTOS){
            productDetailIdLst.add(item.getProductDetailId());
            if(item.getQuantity()>item.getQuantityHave()){
                err = "Số lượng hàng còn lại không đủ để dặt hàng";
            }
        }

        if(StringUtils.isBlank(err)){
            List<ProductDetail> productDetailList = this.productDetailRepository.findAllByIdIn(productDetailIdLst);
            if(orderViewDetailDTO.getStatus()==1){
                for (int i =0 ;i<productDetailList.size();i++){
                    productDetailList.get(i).setQuantity(productDetailList.get(i).getQuantity() - orderDetailForCartDTOS.get(i).getQuantity()); // cập nhật lại số lượng sản phẩm
                }
            }else if(orderViewDetailDTO.getStatus()==4){
                for (int i =0 ;i<productDetailList.size();i++){
                    productDetailList.get(i).setQuantity(productDetailList.get(i).getQuantity() + orderDetailForCartDTOS.get(i).getQuantity()); // cập nhật lại số lượng sản phẩm
                }
            }
            this.productDetailRepository.saveAll(productDetailList);
            Optional<Orders> ordersOP = this.orderRepository.findById(orderViewDetailDTO.getId());
            ordersOP.get().setStatus(orderViewDetailDTO.getStatus());
            return new ServiceResult<>(null,HttpStatus.OK,"");
        }else{
            return new ServiceResult<>(null, HttpStatus.BAD_REQUEST,err);
        }
    }

    public String validatorComment(List<MultipartFile> filesComment, CommentDTO commentDTO){
        String err = "";
        if(filesComment !=null){
            if(filesComment.size()>5){
                err="Danh sách ảnh vieo không được lớn hơn 5";
            }
        }

        if(commentDTO==null){
            err="Không được để nội dung comment trống";
        }else{
            if(commentDTO.getUserID()==null){
                err="Người dùng không tồn tại";
            }else{
                Optional<User> userOP = this.userRepository.findById(commentDTO.getUserID());
                if(userOP.isEmpty()){
                    err="Người dùng không tồn tại";
                }
            }

            if(commentDTO.getProductID()==null){
                err="Không tồn tại sản phẩm này";
            }else{
                Optional<Product> productOP = this.productRepository.findById(commentDTO.getProductID());
                if(productOP.isEmpty()){
                    err="Không tồn tại sản phẩm này";
                }
            }

            if(commentDTO.getUserID()!= null && commentDTO.getProductID()!=null){
                OrderDetail orderDetailList = this.orderDetailRepository.findListOrderCompleteByProductAndUser(commentDTO.getUserID(), commentDTO.getProductID(), commentDTO.getOrderDetailID());
                if(orderDetailList==null){
                    err="Bạn chưa đặt sản phẩm này";
                }

            }


        }
        return err;
    }

    public String validationOrder(OrdersDTO ordersDTO){
        String messErr = "";

        // product
        if(ordersDTO.getOrderDetailDTOList()==null){
            messErr = "Danh sách đặt hàng không được để trống";
            return messErr;
        }else if(ordersDTO.getOrderDetailDTOList().isEmpty()){
            messErr = "Danh sách đặt hàng không tồn tại sản phẩm nào";
            return messErr;
        }else{
            for(int i = 0; i<ordersDTO.getOrderDetailDTOList().size();i++){
                OrderDetailDTO orderDetailDTO = ordersDTO.getOrderDetailDTOList().get(i);

                Optional<Product> optionalProduct = this.productRepository.findById(orderDetailDTO.getProductId());
                if(optionalProduct.isEmpty()){
                    messErr="Sản phẩm không tồn tại";return messErr;
                }else {
                    Product product = optionalProduct.get();

                    List<ProductDetail> productDetailList = this.productDetailRepository.findAllByProductId(product.getId());
                    // kiểm tra xem order sản phẩm có tồn tại đúng type và size hay không
                    Optional<ProductDetail> productDetailOp = this.productDetailRepository.findByTypeIdSizeIdProductId(orderDetailDTO.getTypeProductId(),orderDetailDTO.getSizeProductId(),orderDetailDTO.getProductId());
                    if(productDetailOp.isEmpty()){
                        messErr="Không tồn tại sản phẩm này";return messErr;
                    }else{// nếu tồn tại sản phẩm đó trong bảng productDetail
                        // kiểm tra số lượng đặt hàng có thỏa mãn hay không
                        if(orderDetailDTO.getQuantity()>productDetailOp.get().getQuantity()){
                            messErr = "Số lượng sản phẩm không đủ để đặt hàng";return messErr;
                        }
                    }
                }
                if(orderDetailDTO.getQuantity()<=0L){
                    messErr="Không được chứa sản phẩm có dố lượng đặt hàng nhỏ hơn 1";return messErr;
                }

            }

        }

        // paymentMethod
        if(ordersDTO.getPaymentMethod() == null){
            messErr ="Phương thức thanh toán không được để trống";return messErr;
        }else {
            List<Apprams> appramsList = this.apparamsRepository.findByType("PAYMENT");
            Apprams ap = appramsList.stream().filter(item -> Integer.parseInt(item.getValue()) == ordersDTO.getPaymentMethod()).findFirst().orElse(null);
            if(ap == null) {
                messErr="Phương thức thanh toán không đúng";
                return messErr;
            }
        }

        // user
        if(ordersDTO.getUserId() == null){
            messErr = "Tài khoản đặt hàng không tồn tại";return messErr;
        }else{
            Optional<User> userOp = this.userRepository.findById(ordersDTO.getUserId());
            if(userOp.isEmpty()){
                messErr ="Tài khoản đặt hàng không tồn tại";return messErr;
            }
        }


        // recipientAddress
        if(ordersDTO.getProvinceId()==null || ordersDTO.getDistrictId()==null || ordersDTO.getWard()==null){
            messErr="Địa chỉ người nhận không được để trống";
            return messErr;
        }

        // phoneNumber
        if(StringUtils.isEmpty(ordersDTO.getPhoneNumber())){
            messErr="Số điện thoại người nhận không được để trống";return messErr;
        }

        // sale
        if(ordersDTO.getSaleId()!=null){
            float totalPriceOrder = 0;
            List<OrderDetailDTO> orderDetailDTOS = ordersDTO.getOrderDetailDTOList();

            for(int i=0;i<orderDetailDTOS.size();i++){ // tính tổng số tiền phài trả cho hóa đơn
                Optional<Product> product = this.productRepository.findById(orderDetailDTOS.get(i).getProductId()); // lấy ra sản phầm đặt hàng
                if(product.isPresent()){
                    Optional<Sale> saleOp = this.saleRepository.findByProductID(product.get().getId());
                    if(saleOp.isPresent()){// lấy giá sản phẩm sau khi đã discount sau khi sản phẩm giảm giá
                        orderDetailDTOS.get(i).setPrice(product.get().getPrice()*((100-(saleOp.get().getMaxPurchase()))/100)); // lấy giá của 1 sản phẩm sau khi đã có giảm giá cho sản phẩm0
                        totalPriceOrder += product.get().getPrice()*((100-(saleOp.get().getMaxPurchase()))/100)*orderDetailDTOS.get(i).getQuantity(); // giá của 1 sản phầm nhân với sô lượng ỏdder
                    }else{
                        totalPriceOrder+=product.get().getPrice()*orderDetailDTOS.get(i).getQuantity();
                    }
                }
            }

            Optional<Sale> sale = this.saleRepository.findById(ordersDTO.getSaleId());
            if(sale.isPresent()) {
                Sale sale1 = sale.get();
                float priceDiscount = totalPriceOrder * (sale1.getMaxPurchase() / 100);
                Instant timeNow = Instant.now();
                if (timeNow.isAfter(sale1.getStartTime()) && timeNow.isBefore(sale1.getEndTime()) && sale1.getQuantity() > 0) {
//                    if (totalPriceOrder < sale1.getMinPrice()) { // nếu có điều kiện cho giá hóa đơn thấp nhất
//                        messErr = "Tổng giá của hóa đơn không phù hợp để áp dụng";
//                    }
//
//                    if (sale1.getMaxDiscount() != null) { // nếu mã giảm giá giới hạn số tiền giảm giá
//                        if (priceDiscount > sale1.getMaxDiscount()) {
//                            priceDiscount = totalPriceOrder - sale1.getMaxDiscount();
//                        }
//                    }
                } else {
                    messErr = "Mã giảm giá đã quá hạn hoặc không còn đủ số lượng";
                }

                if (!Objects.equals(sale1.getType(), "1")) {
                    messErr = "Mã giảm giá không đúng";
                }
            }
        }

        return messErr;

    }
}
