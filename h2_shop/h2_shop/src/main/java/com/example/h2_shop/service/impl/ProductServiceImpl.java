package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.*;
import com.example.h2_shop.model.dto.FileDto;
import com.example.h2_shop.model.dto.ProductDTO;
import com.example.h2_shop.model.dto.ProductImgDTO;
import com.example.h2_shop.model.mapper.ProductImgMapper;
import com.example.h2_shop.model.mapper.ProductMapper;
import com.example.h2_shop.model.mapper.SizeMapper;
import com.example.h2_shop.repository.BrandRepository;
import com.example.h2_shop.repository.CategoriesRepository;
import com.example.h2_shop.repository.ProductImgRepository;
import com.example.h2_shop.repository.ProductRepository;
import com.example.h2_shop.service.CategoriesService;
import com.example.h2_shop.service.FileService;
import com.example.h2_shop.service.ProductService;
import com.example.h2_shop.service.ServiceResult;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
    private final CategoriesRepository categoriesRepository;
    private final ProductImgRepository productImgRepository;
    private final ProductImgMapper productImgMapper;

    public ProductServiceImpl(BrandRepository brandRepository,ProductImgMapper productImgMapper,ProductImgRepository productImgRepository,CategoriesRepository categoriesRepository,ProductRepository productRepository,CategoriesService categoriesService){
        this.productRepository=productRepository;
        this.categoriesService=categoriesService;
        this.brandRepository=brandRepository;
        this.categoriesRepository=categoriesRepository;
        this.productImgRepository=productImgRepository;
        this.productImgMapper=productImgMapper;
    }


    @Override
    public ServiceResult<ProductDTO> createProduct(List<MultipartFile> listFileAvatar,ProductDTO productDTO) {

        int countSuccess = 0;
        String errVali = this.validationProduct(productDTO);
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
            Optional<Categories> categoriesOp = this.categoriesRepository.findByParentId(productDTO.getCategoriesID());
            if(categoriesOp.isPresent()){
                Optional<Categories> categoriesParent = this.categoriesRepository.findByParentId(productDTO.getCategoriesID());
                if(!categoriesParent.isPresent()){
                    product.setCategories(categoriesParent.get());
                }
            }
        }
        product.setCreateTime(Instant.now());
        product = this.productRepository.save(product);
        productDTOReturn = this.productMapper.toDto(product);


        if(!listFileAvatar.isEmpty()){
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
        serviceResult.setData(productDTOReturn);
        serviceResult.setStatus(HttpStatus.OK);
        serviceResult.setMessage("Luu thanh cong");
        return serviceResult;
    }

    public String validationProduct(ProductDTO productDTO){
        StringBuilder err=new StringBuilder();

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
}
