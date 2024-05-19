package com.example.h2_shop.service.impl;

import com.example.h2_shop.commons.ReflectorUtil;
import com.example.h2_shop.model.*;
import com.example.h2_shop.model.dto.*;
import com.example.h2_shop.model.mapper.BrandProductMapper;
import com.example.h2_shop.repository.BrandProductRepository;
import com.example.h2_shop.repository.CategoriesRepository;
import com.example.h2_shop.repository.ProductDetailRepository;
import com.example.h2_shop.service.BrandProductService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BrandProductServiceImpl implements BrandProductService {

    @Autowired
    BrandProductRepository brandProductRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    BrandProductMapper brandProductMapper;


    @Override
    public ServiceResult<?> importProduct(ProductDetailResponseDTO productDetailResponseDTO) {

        String err = this.validateImportProduct(productDetailResponseDTO);
        if(!err.isBlank()){
            return new ServiceResult<>(null, HttpStatus.BAD_REQUEST,err);
        }


        List<ProductDetail> lstProductDetail = this.productDetailRepository.findAllByProductId(productDetailResponseDTO.getId());

        List<TypeSizeDTO> productDetails = productDetailResponseDTO.getListProductDetail();

        List<BrandProduct> brandProducts = new ArrayList<>();

        Optional<Categories> categories = this.categoriesRepository.findById(productDetailResponseDTO.getCategoriesID());

        String categoriCode ="";
        if(categories.isPresent()){
            categoriCode = categories.get().getCategoriCode();
        }

        Instant importDate = Instant.now();
        for(int i =0;i<lstProductDetail.size();i++){
            for(int j =0;j<productDetails.size();j++){
                if(lstProductDetail.get(i).getTypeProduct().getId()== productDetails.get(j).getTypeId()
                        && lstProductDetail.get(i).getSize().getId()== productDetails.get(j).getSizeId()
                ){
                    lstProductDetail.get(i).setQuantity(lstProductDetail.get(i).getQuantity()+productDetails.get(j).getQuantity());
                    BrandProduct brandProduct = new BrandProduct();
                    Brands brands = new Brands();
                    brands.setId(productDetailResponseDTO.getBrandId());
                    Product product = new Product();
                    product.setId(productDetailResponseDTO.getId());

                    brandProduct.setBrands(brands);
                    brandProduct.setProduct(product);
                    brandProduct.setImportDate(importDate);
                    brandProduct.setProductDetailId(lstProductDetail.get(i).getId());
                    brandProduct.setImportPrice(productDetailResponseDTO.getPriceImport());
                    brandProduct.setImportQuantity(productDetails.get(j).getQuantity());
                    brandProduct.setCategoryCode(categoriCode);
                    if(productDetails.get(j).getQuantity()>0){
                        brandProducts.add(brandProduct); break;
                    }
                }
            }
        }

        List<BrandProduct> brandProduct = this.brandProductRepository.saveAll(brandProducts);

        List<BrandProductDTO> brandProductDTOS = this.brandProductMapper.toDto(brandProduct);

        return new ServiceResult<>(brandProductDTOS,HttpStatus.OK,"");
    }


    public String validateImportProduct(ProductDetailResponseDTO productDetailResponseDTO){
        String err = "";

        if(productDetailResponseDTO.getId()==null){
            err ="Sản phẩm nhập không được để trống";
        }

        if(productDetailResponseDTO.getBrandId()==null){
            err="Nhà cung cấp không được để trống";
        }

        List<TypeSizeDTO> typeSizeDTOList = productDetailResponseDTO.getTypeSizeDTOS();
        for(int i =0;i<typeSizeDTOList.size();i++){
            if(typeSizeDTOList.get(i).getQuantity()<0){
                err="Số lượng nhập hàng không hợp lệ"; break;
            }
        }

        if(productDetailResponseDTO.getPriceImport()==null){
            err="Giá nhập không được để trống";
        }else{
            if(productDetailResponseDTO.getPriceImport()<=0){
                err="Giá nhập không hợp lệ";
            }
        }

        return err;
    }

    @Override
    public ServiceResult<?> getHistoryImport(Long id) {

        List<Map<String,Object>> map = this.brandProductRepository.getHistoryImportByProductId(id);

        List<ImportHistoryDTO> historyDTOList = map.stream().map(item -> ReflectorUtil.mapToDTO(item, ImportHistoryDTO.class)).collect(Collectors.toList());

        return new ServiceResult<>(historyDTOList, HttpStatus.OK,"");
    }
}
