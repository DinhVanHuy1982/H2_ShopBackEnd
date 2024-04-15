package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.*;
import com.example.h2_shop.model.dto.*;
import com.example.h2_shop.model.mapper.*;
import com.example.h2_shop.repository.*;
import com.example.h2_shop.service.CategoriesService;
import com.example.h2_shop.service.FileService;
import com.example.h2_shop.service.ProductService;
import com.example.h2_shop.service.ServiceResult;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final CategoriesService categoriesService;
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

    public ProductServiceImpl(TypeProductMapper typeProductMapper,ProductDetailRepository productDetailRepository,ProductDetailMapper productDetailMapper,TypeProductRepository typeProductRepository,SizeRepository sizeRepository, BrandRepository brandRepository,BrandProductMapper brandProductMapper,BrandProductRepository brandProductRepository,ProductImgMapper productImgMapper,ProductImgRepository productImgRepository,CategoriesRepository categoriesRepository,ProductRepository productRepository,CategoriesService categoriesService){
        this.productRepository=productRepository;
        this.categoriesService=categoriesService;
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
    }


    @Override
    public ServiceResult<ProductDTO> createProduct(List<MultipartFile> listFileAvatar, ProductDTO productDTO, List<SizeDTO> sizeDTOList, List<TypeProductDTO> typeProductDTOList) {

        int countSuccess = 0;
        String errVali = this.validationProduct(productDTO,sizeDTOList,typeProductDTOList);
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
                Optional<Categories> categoriesParent = this.categoriesRepository.findByParentId(productDTO.getCategoriesID());
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
                        i--;
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
//            for(int i = 0; i<listFileAvatar.size();i++){
//                LocalDateTime currentTime = LocalDateTime.now();
//                String id = currentTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//
//                String fileName = listFileAvatar.get(i).getOriginalFilename();
//                String pathSaveImg = "D:\\IMG_SAVE\\"+id+"_"+fileName;
//                File fileIMG = new File(pathSaveImg);
//                try {
//                    OutputStream opt = new FileOutputStream(fileIMG);
//                    opt.write(listFileAvatar.get(i).getBytes());
//                    Product product = this.productMapper.toEntity(productDTO);
//                    Instant createTime = Instant.now();
//                    product.setCreateTime(createTime);
//
//                    List<Size> listSize = this.sizeMapper.toEntity(productDTO.getSizeDTOList());
//
//
//                    countSuccess++;
//                } catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
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

    public String validationProduct(ProductDTO productDTO, List<SizeDTO> sizeDTOList, List<TypeProductDTO> typeProductDTOList){
        StringBuilder err=new StringBuilder();

        //validation productDetail with size and type
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

        // name
        if(StringUtils.isEmpty(productDTO.getProductName())){
            err.append(" Tên sản phẩm không được để trống ");
        }

        // code
        if(StringUtils.isEmpty(productDTO.getProductCode())){
            err.append(" Mã sản phẩm không được để trống ");
        }else{
            Optional<Product> product = this.productRepository.findByProductCode(productDTO.getProductCode());
            if(product.isPresent()){
                err.append(" Mã sản phẩm đã tồn tại ");
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
                Optional<Categories> categoriesParent = this.categoriesRepository.findByParentId(productDTO.getCategoriesID());
                if(categoriesParent.isPresent()){
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
}
