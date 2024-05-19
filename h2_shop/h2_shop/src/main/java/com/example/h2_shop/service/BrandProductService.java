package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.ProductDetailResponseDTO;
import com.example.h2_shop.model.dto.ProductRequestDTO;
import com.example.h2_shop.model.dto.ProductResponseDTO;

public interface BrandProductService {

    ServiceResult<?> importProduct(ProductDetailResponseDTO productDetailResponseDTO);
    ServiceResult<?> getHistoryImport(Long id);
}
