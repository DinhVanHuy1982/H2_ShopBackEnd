package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.ProductDTO;
import com.example.h2_shop.model.dto.SizeDTO;
import com.example.h2_shop.model.dto.TypeProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public ServiceResult<?> createProduct(List<MultipartFile> listFileAvatar, ProductDTO productDTO, List<SizeDTO> sizeDTOList, List<TypeProductDTO> typeProductDTOList);
    public ServiceResult<?> createComment(List<MultipartFile> listFileAvatar);

    public ServiceResult<ProductDTO> getProductById(Long productId);
}
