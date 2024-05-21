package com.example.h2_shop.service.impl;

import com.example.h2_shop.commons.DataUtil;
import com.example.h2_shop.commons.ReflectorUtil;
import com.example.h2_shop.model.*;
import com.example.h2_shop.model.dto.*;
import com.example.h2_shop.model.mapper.*;
import com.example.h2_shop.repository.*;
import com.example.h2_shop.repository.customRepo.ProductCustomeRepository;
import com.example.h2_shop.service.CategoriesService;
import com.example.h2_shop.service.FileService;
import com.example.h2_shop.service.ProductService;
import com.example.h2_shop.service.ServiceResult;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    SizeMapper sizeMapper;

    @Autowired
    FileService fileService;

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final BrandRepository brandRepository;
    private final BrandProductRepository brandProductRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductImgRepository productImgRepository;
    private final ProductImgMapper productImgMapper;
    private final BrandProductMapper brandProductMapper;
    private final SizeRepository sizeRepository;
    private final TypeProductMapper typeProductMapper;
    private final TypeProductRepository typeProductRepository;
    private final ProductDetailMapper productDetailMapper;
    private final ProductDetailRepository productDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductCustomeRepository productCustomeRepository;
    private final CartRepository cartRepository;

    public ProductServiceImpl(TypeProductMapper typeProductMapper,
                              OrderDetailRepository orderDetailRepository,
                              OrderDetailMapper orderDetailMapper,
                              ProductDetailRepository productDetailRepository,
                              ProductDetailMapper productDetailMapper,
                              TypeProductRepository typeProductRepository,
                              SizeRepository sizeRepository,
                              BrandRepository brandRepository,
                              BrandProductMapper brandProductMapper,
                              BrandProductRepository brandProductRepository,
                              ProductImgMapper productImgMapper,
                              ProductImgRepository productImgRepository,
                              CategoriesRepository categoriesRepository,
                              ProductRepository productRepository,
                              OrderRepository orderRepository,
                              ProductCustomeRepository productCustomeRepository,
                              OrderMapper orderMapper,
                              CartRepository cartRepository,
                              SaleRepository saleRepository){
        this.productRepository=productRepository;
        this.brandRepository=brandRepository;
        this.categoriesRepository=categoriesRepository;
        this.productImgRepository=productImgRepository;
        this.productImgMapper=productImgMapper;
        this.brandProductRepository=brandProductRepository;
        this.brandProductMapper = brandProductMapper;
        this.sizeRepository = sizeRepository;
        this.typeProductMapper=typeProductMapper;
        this.typeProductRepository = typeProductRepository;
        this.productDetailMapper = productDetailMapper;
        this.productDetailRepository=productDetailRepository;
        this.orderDetailMapper=orderDetailMapper;
        this.orderDetailRepository=orderDetailRepository;
        this.productCustomeRepository = productCustomeRepository;
        this.orderRepository=orderRepository;
        this.orderMapper = orderMapper;
        this.cartRepository=cartRepository;
        this.saleRepository=saleRepository;
    }


    @Override
    public ServiceResult<ProductDTO> createProduct(List<MultipartFile> listFileAvatar, ProductDTO productDTO, List<SizeDTO> sizeDTOList, List<TypeProductDTO> typeProductDTOList) {

        int countSuccess = 0;
        String errVali = this.validationProduct(productDTO,sizeDTOList,typeProductDTOList, true);
        ServiceResult<ProductDTO> serviceResult = new ServiceResult<>();
        if(errVali.length()>0){
            serviceResult.setData(null);
            serviceResult.setMessage(errVali);
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);

            return serviceResult;
        }

        ProductDTO productDTOReturn = new ProductDTO();
        Product product = this.productMapper.toEntity(productDTO);
        if(productDTO.getCategoriesID()!=null){
            Optional<Categories> categoriesOp = this.categoriesRepository.findById(productDTO.getCategoriesID());
            if(categoriesOp.isPresent()){ // check xem có tôn tại categori này hay không
                List<Categories> categoriesParent = this.categoriesRepository.findByParentId(productDTO.getCategoriesID());
                if(categoriesParent.isEmpty()){  // check xem categori có là danh mục cha của 1 danh mục nào đó hay không
                    product.setCategories(categoriesOp.get());
                }
            }
        }

        List<ProductDetailDTO> productDetailDTOList = productDTO.getListProductDetail();// sản phẩm có số lượng được thêm vào bảng
        // size
        List<Size> sizeList = new ArrayList<>();
        if(sizeDTOList!=null){
             sizeList = this.sizeMapper.toEntity(sizeDTOList);
            for(int i=0;i<sizeList.size();i++){
                sizeList.get(i).setCreateName(sizeList.get(i).getSizeName());
                sizeList.get(i).setCreateTime(Instant.now());
            }
            sizeList = this.sizeRepository.saveAll(sizeList);

            for(int i=0;i<productDetailDTOList.size();i++){
                productDetailDTOList.get(i).setSize(sizeList.get(productDetailDTOList.get(i).getPositionSize()-1));
            }
        }
        //typeProduct
        List<TypeProduct> typeProductList = new ArrayList<>();
        if(typeProductDTOList!=null){
            typeProductList = this.typeProductMapper.toEntity(typeProductDTOList);
            for(int i=0;i<typeProductList.size();i++){
                typeProductList.get(i).setCreateTime(Instant.now());
            }
            typeProductList = this.typeProductRepository.saveAll(typeProductList);

            for(int i=0;i<productDetailDTOList.size();i++){
                productDetailDTOList.get(i).setTypeProduct(typeProductList.get(productDetailDTOList.get(i).getPositionType()-1));
            }
        }

        product.setCreateTime(Instant.now());
        product = this.productRepository.save(product);
        if(productDTO.getBrandId()!=null){
            BrandProduct brandProduct = new BrandProduct();
            Brands brands = new Brands();
            brands.setId(productDTO.getBrandId());
            brandProduct.setProduct(product);
            brandProduct.setBrands(brands);
        }

        // lấy ra danh sách các loại và kiểu chưa được xếp số lượng thì gán chúng bằng 0
        List<ProductDetailDTO> productDetailDTOS = new ArrayList<>(); // danh sách productDetail không có số lượng
        if(!sizeList.isEmpty() && !typeProductList.isEmpty()){

            // lấy ra danh sách kết hợp tất cả các trường hợp có thể xảy ra khi kết hợp size và type
            for(int i=0;i<sizeList.size();i++){
                for (int j=0;j<typeProductList.size();j++){
                    ProductDetailDTO productDetailDTO = new ProductDetailDTO();
                    productDetailDTO.setSize(sizeList.get(i));
                    productDetailDTO.setTypeProduct(typeProductList.get(j));
                    productDetailDTO.setProduct(product);
                    productDetailDTO.setQuantity(0L);
                    productDetailDTOS.add(productDetailDTO);
                }
            }

            // loại bỏ những bản ghi orderDeatil đã được lưu trước đó
            for(int i=0;i<productDetailDTOS.size();i++){
                for(int j=0;j<productDetailDTOList.size();j++){
                    if(productDetailDTOS.get(i).getSize().getId() == productDetailDTOList.get(j).getSize().getId()
                            && productDetailDTOS.get(i).getTypeProduct().getId() == productDetailDTOList.get(j).getTypeProduct().getId()){
                        productDetailDTOS.remove(i);
                        i--;break;
                    }
                }
            }
            List<ProductDetail> productDetails = this.productDetailMapper.toEntity(productDetailDTOS);
            productDetails = this.productDetailRepository.saveAll(productDetails);
        }

        for (int i=0;i<productDetailDTOList.size();i++){
            productDetailDTOList.get(i).setProduct(product);
        }

        List<ProductDetail> productDetailList = this.productDetailMapper.toEntity(productDetailDTOList);
        productDetailList = this.productDetailRepository.saveAll(productDetailList);
        productDetailDTOList = this.productDetailMapper.toDto(productDetailList);

        productDTOReturn = this.productMapper.toDto(product);
        productDTOReturn.setListProductDetail(productDetailDTOList);


        if(listFileAvatar!=null){
            ServiceResult<List<FileDto>> fileDtoServiceResult = this.fileService.createListFile(listFileAvatar);
            List<ProductImg> productImgList = new ArrayList<>();
            if(fileDtoServiceResult.getStatus()==HttpStatus.OK){
                List<FileDto> fileDtos = fileDtoServiceResult.getData();
                for(int i=0;i<fileDtos.size();i++){
                    ProductImg productImg = new ProductImg();
                    if(i==0){
                        productImg.setAvatar(true);
                    }
                    productImg.setType("IMGPRODUCT");
                    productImg.setProduct(product);
                    productImg.setFileId(fileDtos.get(i).getFileId());
                    productImg.setFileName(fileDtos.get(i).getFileName());
                    productImg.setFileSize(fileDtos.get(i).getFileSize()+" byte");
                    productImgList.add(productImg);
                }
                productImgList = this.productImgRepository.saveAll(productImgList);
                List<ProductImgDTO> productImgDTOList = this.productImgMapper.toDto(productImgList);
                productDTOReturn.setProductImgDTOList(productImgDTOList);
            }
        }

        if(productDTO.getBrandId()!=null){
             BrandProduct brandProduct = new BrandProduct();
             brandProduct.setProduct(product);
             brandProduct.setBrands(this.brandRepository.findById(productDTO.getBrandId()).get());
             brandProduct.setImportDate(Instant.now());
             brandProduct = this.brandProductRepository.save(brandProduct);
             BrandProductDTO brandProductDTO = this.brandProductMapper.toDto(brandProduct);
             brandProductDTO.setCategoryCode(productDTOReturn.getCategories().getCategoriCode());
             productDTOReturn.setBrandProductDTO(brandProductDTO);
        }

        serviceResult.setData(productDTOReturn);
        serviceResult.setStatus(HttpStatus.OK);
        serviceResult.setMessage("Luu thanh cong");
        return serviceResult;
    }

    @Override
    public ServiceResult<?> updateProduct(List<MultipartFile> listFileAvatar,ProductDetailResponseDTO productResponseDTO, List<SizeDTO> sizeDTOList, List<TypeProductDTO> typeProductDTOList) {

        Optional<Product> productOP = this.productRepository.findById(productResponseDTO.getId());
        Product product = new Product();
        List<Categories> categoriesOP = this.categoriesRepository.findByParentId(productResponseDTO.getCategoriesID());
        if(!categoriesOP.isEmpty()){
            return new ServiceResult<>(null, HttpStatus.BAD_REQUEST,"Danh mục sản phẩm phải là cấp thấp nhất");
        }
        List<BrandProduct> lstBrandProduct = this.brandProductRepository.findByProductId(productResponseDTO.getId());
        Optional<Brands> brandsOP = this.brandRepository.findById(productResponseDTO.getBrandId());
        if(!brandsOP.isPresent()){
            return new ServiceResult<>(null, HttpStatus.BAD_REQUEST,"Nhãn hàng không tồn tại");
        }

        if(productOP.isPresent()){
            product=productOP.get();
            product.setId(productResponseDTO.getId());
            product.setProductCode(product.getProductCode());
            Categories categories = new Categories();
            categories.setId(productResponseDTO.getCategoriesID());
            product.setCategories(categories);
            product.setPrice(productResponseDTO.getPrice());
            product.setDescription(productResponseDTO.getDescription());
            product.setProductName(productResponseDTO.getProductName());

            for(BrandProduct bp:lstBrandProduct){
                bp.setBrands(brandsOP.get());
            }

        }else{
            return new ServiceResult<>(null, HttpStatus.BAD_REQUEST,"Sản phẩm không tồn tại");
        }

        String err = this.validationProduct(this.productMapper.toDto(product),null,null,false);
        if(!err.isBlank()){
            return new ServiceResult<>(null,HttpStatus.BAD_REQUEST,err);
        }

        List<ProductDetail> productDetails = new ArrayList<>(); // lấy ra danh sách productdetail không bị ảnh hưởng bởi thay đổi trang thái
        List<TypeSizeDTO> listTypeSize = productResponseDTO.getListProductDetail();
        for(int i =0;i< listTypeSize.size();i++){
            if(listTypeSize.get(i).getTypeId()!=null && listTypeSize.get(i).getSizeId()!=null){
                Optional<ProductDetail> productDetailOP = this.productDetailRepository.findByTypeIdSizeIdProductId( listTypeSize.get(i).getTypeId(), listTypeSize.get(i).getSizeId(),productResponseDTO.getId());
                if(productDetailOP.isPresent()){
                    productDetails.add(productDetailOP.get());
                }
            }
        }

        List<ProductDetail> productDetailListAllOfProduct = this.productDetailRepository.findAllByProductId(productResponseDTO.getId());// lấy ra danh sách productdetail thuộc sản phẩm trước khi cập nhật

        // lấy ra danh sách productDetail cần xóa
        List<ProductDetail> productDetailDelete = new ArrayList<>(productDetailListAllOfProduct);
        productDetailDelete.removeAll(productDetails);
        for(int i =0;i<productDetailDelete.size();i++){
            if(productDetailDelete.get(i).getQuantity()!=0){
                return new ServiceResult<>("",HttpStatus.BAD_REQUEST, "Loại hoặc kích cỡ xóa đang còn trong kho, không được xóa");
            }
        }

        List<TypeProduct> lstNewType = this.typeProductMapper.toEntity(typeProductDTOList);
        for(int i=0;i<lstNewType.size();i++){
            if(StringUtils.isBlank(lstNewType.get(i).getTypeName())){
                return  new ServiceResult<>("",HttpStatus.BAD_REQUEST,"Tên loại không được để trống");
            }else{
                lstNewType.get(i).setCreateTime(Instant.now());
            }
        }


        List<Size> lstNewSize = this.sizeMapper.toEntity(sizeDTOList);
        for(int i=0;i<lstNewSize.size();i++){
            if(StringUtils.isBlank(lstNewSize.get(i).getSizeName())){
                return  new ServiceResult<>("",HttpStatus.BAD_REQUEST,"Kích cỡ không được để trống");
            }else{
                lstNewSize.get(i).setCreateTime(Instant.now());
            }
        }



        List<Size> sizeList = this.sizeRepository.saveAll(lstNewSize);
        List<TypeProduct> typeProductList = this.typeProductRepository.saveAll(lstNewType);

        List<ProductDetail> allPdUpdate = new ArrayList<>();
        for(int i=0;i<sizeList.size();i++){
            for(int j=0;j<typeProductList.size();j++){
                ProductDetail productDetail = new ProductDetail();
                productDetail.setTypeProduct(typeProductList.get(j));
                productDetail.setSize(sizeList.get(i));
                productDetail.setProduct(product);
                productDetail.setQuantity(0L);
                allPdUpdate.add(productDetail);
            }
        }

        for(int i=0;i<allPdUpdate.size();i++){
            for(int j=0;j<productDetails.size();j++){
                if(allPdUpdate.get(i).getSize().getId() == productDetails.get(j).getSize().getId()
                    && allPdUpdate.get(i).getTypeProduct().getId() == productDetails.get(j).getTypeProduct().getId()
                ){
                    allPdUpdate.remove(i);i--;break;
                }
            }
        }
        this.productDetailRepository.saveAll(allPdUpdate);
        this.productDetailRepository.deleteAll(productDetailDelete);

        if(listFileAvatar!=null){
            ServiceResult<List<FileDto>> fileDtoServiceResult = this.fileService.createListFile(listFileAvatar);
            List<ProductImg> productImgList = new ArrayList<>();
            if(fileDtoServiceResult.getStatus()==HttpStatus.OK){
                List<FileDto> fileDtos = fileDtoServiceResult.getData();
                for(int i=0;i<fileDtos.size();i++){
                    ProductImg productImg = new ProductImg();
                    if(i==0){
                        productImg.setAvatar(true);
                    }
                    productImg.setType("IMGPRODUCT");
                    productImg.setProduct(product);
                    productImg.setFileId(fileDtos.get(i).getFileId());
                    productImg.setFileName(fileDtos.get(i).getFileName());
                    productImg.setFileSize(fileDtos.get(i).getFileSize()+" byte");
                    productImgList.add(productImg);
                }
                this.productImgRepository.saveAll(productImgList);
            }
        }
        // xóa những file người dùng đã xóa
        this.productImgRepository.deleteAllById(productResponseDTO.getImgDelete());

        return new ServiceResult<>("",HttpStatus.OK,"Cập nhật thành công");
    }

    public String validationProduct(ProductDTO productDTO, List<SizeDTO> sizeDTOList, List<TypeProductDTO> typeProductDTOList, boolean isCreate){
        StringBuilder err=new StringBuilder();

        //validation productDetail with size and type
        if(isCreate){
            List<ProductDetailDTO> productDetailDTOList = productDTO.getListProductDetail();
            if(productDetailDTOList!=null && sizeDTOList!=null && typeProductDTOList!=null){
                for(int i=0;i<productDetailDTOList.size();i++){
                    if(productDetailDTOList.get(i).getPositionSize()>sizeDTOList.size()){
                        err.append("Thứ tự size sản phẩm không đúng");break;
                    }else if(productDetailDTOList.get(i).getPositionType()>typeProductDTOList.size()){
                        err.append("Thứ tự loại sản phẩm không đúng");break;
                    }
                }
            }

            //validation size
            if(sizeDTOList!=null){
                for (int i=0;i<sizeDTOList.size();i++){
                    if(StringUtils.isEmpty(sizeDTOList.get(i).getSizeName())){
                        err.append("Size sản phẩm không được để trống");break;
                    }
                }
            }

            //validation typeProduct
            if(typeProductDTOList!=null){
                for(int i=0;i<typeProductDTOList.size();i++){
                    if(StringUtils.isEmpty(typeProductDTOList.get(i).getTypeName())){
                        err.append("Loại sản phẩm không được để trống");break;
                    }
                }
            }
        }

        // name
        if(StringUtils.isEmpty(productDTO.getProductName())){
            err.append(" Tên sản phẩm không được để trống ");
        }

        // code
        if(StringUtils.isEmpty(productDTO.getProductCode())){
            err.append(" Mã sản phẩm không được để trống ");
        }else{
            Optional<Product> product = this.productRepository.findByProductCode(productDTO.getProductCode());
            if(isCreate){
                if(product.isPresent()){
                    err.append(" Mã sản phẩm đã tồn tại ");
                }
            }else{
                if(!product.isPresent()){
                    err.append(" Không tồn tại sản phẩm này ");
                }
            }
        }

        //price
        if(productDTO.getPrice()==null){
            err.append(" Không được để trống giá sản phẩm ");
        }else{
            if(productDTO.getPrice()<0L){
                err.append(" Giá sản phẩm khồng được nhỏ hơn 0 ");
            }
        }

        //categories
        if(productDTO.getCategoriesID()!=null){
            Optional<Categories> categoriesOp = this.categoriesRepository.findById(productDTO.getCategoriesID());
            if(categoriesOp.isPresent()){
                List<Categories> categoriesParent = this.categoriesRepository.findByParentId(productDTO.getCategoriesID());
                if(!categoriesParent.isEmpty()){
                    err.append(" Danh mục phải chọn cấp thấp nhất ");
                }
            }else{
                err.append(" Danh mục không tồn tại ");
            }
        }

        //brand
        if(productDTO.getBrandId()!=null){
            Optional<Brands> brands = this.brandRepository.findById(productDTO.getBrandId());
            if(brands.isEmpty()){
                err.append(" Nhãn hiệu không tồn tại ");
            }
        }



        return err.toString();
    }

    @Override
    public ServiceResult<?> createComment(List<MultipartFile> listFileAvatar) {
        int countSuccess = 0;
        if(!listFileAvatar.isEmpty()){
            for(int i = 0; i<listFileAvatar.size();i++){
                LocalDateTime currentTime = LocalDateTime.now();
                String id = currentTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

                String fileName = listFileAvatar.get(i).getOriginalFilename();
                String pathSaveImg = "D:\\IMG_SAVE\\"+id+"_"+fileName;
                File fileIMG = new File(pathSaveImg);
                try {
                    OutputStream opt = new FileOutputStream(fileIMG);
                    opt.write(listFileAvatar.get(i).getBytes());
                    countSuccess++;
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if(countSuccess!=0){
            return new ServiceResult<>("Thanh cong", HttpStatus.OK,"Thành công "+ countSuccess+"/"+listFileAvatar.size());
        }else{
            return new ServiceResult<>("That bai", HttpStatus.BAD_REQUEST,"Thhất bại" );
        }
    }

    @Override
    public ServiceResult<ProductDTO> getProductById(Long productId) {
        ServiceResult<ProductDTO> serviceResult = new ServiceResult<>();
        if(productId==null){
            serviceResult.setMessage("Cần truyền id của sản phẩm");
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            return serviceResult;
        }else{
            Optional<Product> productOP = this.productRepository.findById(productId);
            if(productOP.isEmpty()){
                serviceResult.setMessage("Sản phẩm không tồn tại");
                serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                return serviceResult;
            }else{
                Product product = productOP.get();
                ProductDTO productDTO = this.productMapper.toDto(product);

                List<ProductDetail> productDetail = this.productDetailRepository.findAllByProductId(productId);
                if(productDetail!=null){
                    List<ProductDetailDTO> productDetailDTO = this.productDetailMapper.toDto(productDetail);

                    Set<Size> sizeSet = new HashSet<>();
                    Set<TypeProduct> typeProductsSet = new HashSet<>();
                    for(int i=0;i<productDetailDTO.size();i++){
                        productDetailDTO.get(i).setProduct(null);
                        sizeSet.add(this.sizeRepository.findById(productDetailDTO.get(i).getSize().getId()).get());
                        typeProductsSet.add(this.typeProductRepository.findById(productDetailDTO.get(i).getTypeProduct().getId()).get());
                    }
                    List<Size> sizeList = new ArrayList<>(sizeSet);
                    List<TypeProduct> typeProductList = new ArrayList<>(typeProductsSet);

                    List<SizeDTO> sizeDTOList = this.sizeMapper.toDto(sizeList);
                    List<TypeProductDTO> typeProductDTOList = this.typeProductMapper.toDto(typeProductList);



                    productDTO.setSizeDTOList(sizeDTOList);
                    productDTO.setTypeProductDTOList(typeProductDTOList);
                    productDTO.setListProductDetail(productDetailDTO);
                }
                serviceResult.setData(productDTO);
                serviceResult.setStatus(HttpStatus.OK);
                return serviceResult;
            }

        }
    }

    @Override
    public ServiceResult<Page<ProductResponseDTO>> getPageProduct(ProductRequestDTO productRequestDTO) {

        Pageable pageable = PageRequest.of(productRequestDTO.getPage()-1,productRequestDTO.getPageSize());

        Page<Map<String,Object>> pageMap = this.productRepository.getPageProductView(pageable, productRequestDTO.getNameSearch(), productRequestDTO.getBrandId() , productRequestDTO.getCategoriesId());

        List<Map<String,Object>> map = pageMap.getContent();
        List<ProductResponseDTO> productResponseDTOList = map.stream().map(item -> ReflectorUtil.mapToDTO(item,ProductResponseDTO.class)).collect(Collectors.toList());

        Page<ProductResponseDTO> pageReturn = new PageImpl<>(productResponseDTOList, pageable, pageMap.getTotalElements());

        return new ServiceResult<>(pageReturn,HttpStatus.OK,"Success");
    }

    @Override
    public ServiceResult<List<ProductDTO>> getAllProduct() {
        List<Product> lstProduct= this.productRepository.findAllByOrderByProductCodeAsc();
        List<ProductDTO> productDTOList = this.productMapper.toDto(lstProduct);
        for (int i=0;i<productDTOList.size();i++){
            if(productDTOList.get(i).getCategories()!=null){
                productDTOList.get(i).setCategoriesID(productDTOList.get(i).getCategories().getId());
                productDTOList.get(i).setCategories(null);
            }

        }
        return new ServiceResult<>(productDTOList,HttpStatus.OK,"");
    }


    @Override
    public ServiceResult<ProductDetailResponseDTO> detailProductById(Long id) {
        Optional<Product> productOP = this.productRepository.findById(id);
        if(productOP.isPresent()){
            Brands brands = this.brandRepository.getByProductId(id);

            ProductDTO productDTO = this.productMapper.toDto(productOP.get());
            List<ProductDetail> productDetailList = this.productDetailRepository.findAllByProductId(productDTO.getId());
            productDTO.setListProductDetail(this.productDetailMapper.toDto(productDetailList));

            ProductDetailResponseDTO productDetailResponseDTO = new ProductDetailResponseDTO();

            // set thông tin chung
            productDetailResponseDTO.setProductCode(productDTO.getProductCode());
            productDetailResponseDTO.setProductName(productDTO.getProductName());
            productDetailResponseDTO.setId(productDTO.getId());
            productDetailResponseDTO.setDescription(productDTO.getDescription());
            productDetailResponseDTO.setPrice(productDTO.getPrice());
            productDetailResponseDTO.setCategoriesID(productDTO.getCategories().getId());
            productDetailResponseDTO.setBrandId(productDTO.getBrandId());
            productDetailResponseDTO.setBrandId(brands.getId());

            // lấy danh sách đường dẫn ảnh
            List<ProductImg> productImgList = this.productImgRepository.findByProductId(productDTO.getId());
            List<ProductImgDTO> productImgDTOList = this.productImgMapper.toDto(productImgList);
            List<String> fileNameLst = new ArrayList<>();
            productImgList.forEach(item -> fileNameLst.add(item.getFileName()));
            productDetailResponseDTO.setPathImg(fileNameLst);
            productDetailResponseDTO.setLstProductIMG(productImgDTOList);

            // lấy danh sách size
            List<SizeDTO> lstSizeDTO = this.sizeMapper.toDto(this.sizeRepository.getSizeOfProduct(productDTO.getId()));
            for(int i =0;i< lstSizeDTO.size();i++){
                lstSizeDTO.get(i).setPosition(i+1);
            }
            productDetailResponseDTO.setSizeDTOS(lstSizeDTO);

            // lấy danh sách các type
            List<TypeProductDTO> lstTypeProduct = this.typeProductMapper.toDto(this.typeProductRepository.getTypeOfProduct(productDTO.getId()));
            for(int i =0;i< lstTypeProduct.size();i++){
                lstTypeProduct.get(i).setPosition(i+1);
            }
            productDetailResponseDTO.setTypeProductDTOS(lstTypeProduct);

            // lấy ra typeSize
            List<Map<String , Object>> map = this.typeProductRepository.getSizeTypeDTO(productDTO.getId());
            List<TypeSizeDTO> typeSizeDTOList = map.stream().map(item -> ReflectorUtil.mapToDTO(item,TypeSizeDTO.class)).collect(Collectors.toList());
            for(int i=0;i<lstSizeDTO.size();i++){
                for(int j=0;j<lstTypeProduct.size();j++){
                    int finalI = i;
                    int finalJ = j;
                    typeSizeDTOList.forEach(item -> {
                        if(item.getSizeId()==lstSizeDTO.get(finalI).getId() && item.getTypeId()==lstTypeProduct.get(finalJ).getId()){
                            item.setPositionSize(Long.parseLong((finalI+1) +""));
                            item.setPositionType(Long.parseLong((finalJ+1)+""));
                        }
                    });
                }
            }
            productDetailResponseDTO.setTypeSizeDTOS(typeSizeDTOList);

            return new ServiceResult<>(productDetailResponseDTO,HttpStatus.OK,"Success");
        }else{
            return null;
        }
    }

    @Override
    public ServiceResult<ProductDetailForClientDTO> detailProductForHome(Long id) {
        Optional<Product> productOP = this.productRepository.findById(id);
        if(productOP.isPresent()){

            ProductDTO productDTO = this.productMapper.toDto(productOP.get());
            List<ProductDetail> productDetailList = this.productDetailRepository.findAllByProductId(productDTO.getId());
            productDTO.setListProductDetail(this.productDetailMapper.toDto(productDetailList));

            ProductDetailForClientDTO productDetailResponseDTO = new ProductDetailForClientDTO();

            // set thông tin chung
            productDetailResponseDTO.setProductCode(productDTO.getProductCode());
            productDetailResponseDTO.setProductName(productDTO.getProductName());
            productDetailResponseDTO.setId(productDTO.getId());
            productDetailResponseDTO.setDescription(productDTO.getDescription());
            productDetailResponseDTO.setPrice(productDTO.getPrice());
            productDetailResponseDTO.setCategoriesId(productDTO.getCategories().getId());
            productDetailResponseDTO.setBrandId(productDTO.getBrandId());

            // kiểm tra sản phẩm có thuộc chương trình giảm giá nào hay không
            Optional<Sale> saleOP = this.saleRepository.findByProductID(id);
            if(saleOP.isPresent()){
                productDetailResponseDTO.setMaxPurchase(saleOP.get().getMaxPurchase());
                Double priceSale = Double.parseDouble( productDetailResponseDTO.getPrice()* ((100 - saleOP.get().getMaxPurchase()) / 100) +"");
                productDetailResponseDTO.setPriceSale(priceSale);
            }

            // lấy danh sách đường dẫn ảnh
            List<ProductImg> productImgList = this.productImgRepository.findByProductId(productDTO.getId());
            List<ProductImgDTO> productImgDTOList = this.productImgMapper.toDto(productImgList);
            List<String> fileNameLst = new ArrayList<>();
            productImgList.forEach(item -> fileNameLst.add(item.getFileName()));
            productDetailResponseDTO.setPathImg(fileNameLst);
            productDetailResponseDTO.setLstProductIMG(productImgDTOList);

            // lấy danh sách size
            List<SizeDTO> lstSizeDTO = this.sizeMapper.toDto(this.sizeRepository.getSizeOfProduct(productDTO.getId()));
            productDetailResponseDTO.setSizeDTOS(lstSizeDTO);

            // lấy danh sách các type
            List<TypeProductDTO> lstTypeProduct = this.typeProductMapper.toDto(this.typeProductRepository.getTypeOfProduct(productDTO.getId()));
            productDetailResponseDTO.setTypeProductDTOS(lstTypeProduct);

            // lấy ra typeSize
            List<Map<String , Object>> map = this.typeProductRepository.getSizeTypeDTO(productDTO.getId());
            List<TypeSizeDTO> typeSizeDTOList = map.stream().map(item -> ReflectorUtil.mapToDTO(item,TypeSizeDTO.class)).collect(Collectors.toList());
            for(int i=0;i<lstSizeDTO.size();i++){
                for(int j=0;j<lstTypeProduct.size();j++){
                    int finalI = i;
                    int finalJ = j;
                    typeSizeDTOList.forEach(item -> {
                        if(item.getSizeId()==lstSizeDTO.get(finalI).getId() && item.getTypeId()==lstTypeProduct.get(finalJ).getId()){
                            item.setPositionSize(Long.parseLong((finalI+1) +""));
                            item.setPositionType(Long.parseLong((finalJ+1)+""));
                        }
                    });
                }
            }
            productDetailResponseDTO.setTypeSizeDTOS(typeSizeDTOList);

            productDetailResponseDTO.setCommentResponseDTO(getDetailComment(id).getData());

//            // lấy sao đánh giá cho sản phẩm
            List<OrderDetail> lstObj = this.orderDetailRepository.getOrderByIdProduct(id);
            Long totalSold=0l;
            for(OrderDetail o : lstObj){
                totalSold+=o.getQuantity();
            }
            productDetailResponseDTO.setTotalSold(totalSold);
            return new ServiceResult<>(productDetailResponseDTO,HttpStatus.OK,"Success");
        }else{
            return null;
        }
    }

    @Override
    public ServiceResult<List<ProductBestSellerDTO>> getListBestSeller() {
        List<Map<String,Object>> mapStrObj = this.productRepository.getListBestSeller();
        List<ProductBestSellerDTO> lst = mapStrObj.stream().map(item -> ReflectorUtil.mapToDTO(item,ProductBestSellerDTO.class)).collect(Collectors.toList());
        return new ServiceResult<>(lst,HttpStatus.OK,"Thành công");
    }

    @Override
    public ServiceResult<CommentResponseDTO> getDetailComment(Long id) {
        Map<String,Object> map = this.productRepository.getInforCommentProduct(id);
        CommentResponseDTO commentResponseDTO = ReflectorUtil.mapToDTO(map,CommentResponseDTO.class);

        // lấy danh sách những đơn mua mua sản phẩm có id đc truyền vào
        List<OrderDetail> orderDetailList = this.orderDetailRepository.getOrderByIdProduct(id);

        Long sumRate = 0L;

        List<CommentByUser> commentByUserList = new ArrayList<>();
        for(OrderDetail orderDetail:orderDetailList){
            List<String> imgCommentLst = new ArrayList<>();
            List<String> imgRepplyLst = new ArrayList<>();
            CommentByUser commentByUser = ReflectorUtil.mapToDTO(orderDetailRepository.detaiCommentByUser(orderDetail.getId()), CommentByUser.class);
            List<Map<String,Object>> lstMapImg = orderDetailRepository.getImgCommentAndRepply(orderDetail.getId());
            for(Map<String,Object> mapItem: lstMapImg){
                if(mapItem.get("type").toString().equals("IMGCMT")){
                    imgCommentLst.add(mapItem.get("file_name").toString());
                }else{
                    imgRepplyLst.add(mapItem.get("file_name").toString());
                }
            }
            if(commentByUser.getUserId()!=null){
                commentByUser.setImgComment(imgCommentLst);
                commentByUser.setImgCommentRepply(imgRepplyLst);
                sumRate+=commentByUser.getRating();
                commentByUserList.add(commentByUser);
            }
        }
        if(commentResponseDTO.getTotalRate()!=0){
            commentResponseDTO.setAvgRate((double) sumRate/commentResponseDTO.getTotalRate());
        }
        commentResponseDTO.setUserComment(commentByUserList);

        return new ServiceResult<>(commentResponseDTO, HttpStatus.OK, "success");
    }

    @Override
    public ServiceResult<Page<ProductSearchResponse>> searchProductForUser(ProductRequestDTO productRequestDTO) {
        return new ServiceResult<>(this.productCustomeRepository.searchProduct(productRequestDTO), HttpStatus.OK,"");
    }

    @Override
    public ServiceResult<List<ProductImgDTO>> getListBanner() {

        List<ProductImg> productImgList = this.productImgRepository.findByType("BANNER");
        List<ProductImgDTO> productImgDTOList = this.productImgMapper.toDto(productImgList);


        return new ServiceResult<>(productImgDTOList,HttpStatus.OK,"");
    }

    @Override
    public ServiceResult<OrdersDTO> updatePayStatusOrder(String orderCode) {

        Optional<Orders> ordersOP = this.orderRepository.findByOrderCode(orderCode);

        if(ordersOP.isPresent()){
            ordersOP.get().setPayStatus(1L);
            return new ServiceResult<>(this.orderMapper.toDto(ordersOP.get()),HttpStatus.OK,"Đặt hàng thành công");
        }else{
            return new ServiceResult<>(null,HttpStatus.BAD_REQUEST,"Đặt hàng thất bại");
        }
    }

    @Override
    public ServiceResult<List<ProductImgDTO>> createBanner(List<MultipartFile> lstBanner,List<Long> lstIdDelete) {

        List<ProductImg> productImgList = this.productImgRepository.findByType("BANNER");

        if(lstBanner!=null){
            if(lstBanner.size()+ (productImgList.size()-lstIdDelete.size())>8){
                return new ServiceResult<>(null,HttpStatus.BAD_REQUEST, "Số lượng banner không được nhiều hơn 8");
            }else{

                List<FileDto> lstFile = this.fileService.createListFile(lstBanner).getData();

                List<ProductImg> productImgs = new ArrayList<>();
                for(int i=0;i<lstFile.size();i++){
                    ProductImg productImg = new ProductImg();

                    productImg.setFileId(lstFile.get(i).getFileId());
                    productImg.setType("BANNER");
                    productImg.setFileName(lstFile.get(i).getFileName());
                    productImg.setFileSize(lstFile.get(i).getFileSize()+" byte");
                    productImgs.add(productImg);
                }

                productImgs = this.productImgRepository.saveAll(productImgs);
                List<ProductImgDTO> lst = this.productImgMapper.toDto(productImgs);
                return  new ServiceResult<>(lst,HttpStatus.OK,"");

            }
        }
        if(!lstIdDelete.isEmpty()){
            this.productImgRepository.deleteAllById(lstIdDelete);
        }
        return  new ServiceResult<>(null,HttpStatus.OK,"");

    }

    @Override
    public ServiceResult<ProductDTO> deleteProduct(Long id) {
        ServiceResult<ProductDTO> serviceResult = new ServiceResult<>();
        Optional<Product> productOP = productRepository.findById(id);
        if(productOP.isPresent()){
            this.productDetailRepository.findAllByProductId(id);

            // kiểm tra sản phẩm đã được đặt hàng hay chưa
            List<Map<String,Object>> mapOrderDetail = this.productRepository.getOrderOfProduct(id);
            if(mapOrderDetail.isEmpty()){
                Long quantityImport = this.brandProductRepository.sumQuantityByProduct(id);
                if(quantityImport>0){
                    serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                    serviceResult.setMessage("Sản phẩm đã được nhập hàng, không được xóa");
                }else{
                    // xóa bản ghi trong brand_product
                    this.brandProductRepository.deleteByProductId(id);

                    List<ProductDetail> productDetails = this.productDetailRepository.findAllByProductId(id);

                    List<TypeProduct> typeProductsDelete = new ArrayList<>();
                    List<Size> sizeDelete = new ArrayList<>();
                    List<Long> idProductDetails = new ArrayList<>();
                    for( int i = 0 ;i<productDetails.size();i++){
                        typeProductsDelete.add(productDetails.get(i).getTypeProduct());
                        sizeDelete.add(productDetails.get(i).getSize());
                        idProductDetails.add(productDetails.get(i).getId());
                    }
                    this.cartRepository.deleteCartWithProductId(idProductDetails);
                    this.productDetailRepository.deleteAll(productDetails);
                    this.sizeRepository.deleteAll(sizeDelete);
                    this.typeProductRepository.deleteAll(typeProductsDelete);

                    this.productImgRepository.deleteAllByProductId(id);
                    this.saleRepository.deleteAllByProductId(id);
                    this.productRepository.delete(productOP.get());

//                    serviceResult.setData(this.productMapper.toDto(productOP.get()));
                    serviceResult.setStatus(HttpStatus.OK);
                    serviceResult.setMessage("Xóa sản phẩm thành công");
                }
            }else{
                serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                serviceResult.setMessage("Sản phẩm đã được mua, không được xóa");
            }
        }else{
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setMessage("Sản phẩm không tồn tại");
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<SaleForProductDTO> getLstBestSaleForDay() {
        SaleForProductDTO saleForProductDTO = new SaleForProductDTO();
        Optional<Sale> saleOP = this.saleRepository.getSaleHaveMaxpurchaseForDate();
        if(saleOP.isPresent()){
            List<Map<String,Object>> map = this.productRepository.getLstProductSaleForSaleCode(saleOP.get().getCode());
            List<ProductBestSellerDTO> lstProductBestSale = map.stream().map(item -> ReflectorUtil.mapToDTO(item,ProductBestSellerDTO.class)).collect(Collectors.toList());
            saleForProductDTO.setCode(saleOP.get().getCode());
            saleForProductDTO.setMaxPurchase(saleOP.get().getMaxPurchase());
            saleForProductDTO.setEndDate(saleOP.get().getEndTime());
            saleForProductDTO.setStartDate(saleOP.get().getStartTime());
            saleForProductDTO.setLstProductBestSale(lstProductBestSale);
            return new ServiceResult<>(saleForProductDTO,HttpStatus.OK,"");
        }else {
            return new ServiceResult<>(null,HttpStatus.BAD_REQUEST,"Không tồn tại chương trình giảm giá nào");
        }
    }
}
