package com.example.h2_shop.repository.customRepo;

import com.example.h2_shop.model.dto.ProductRequestDTO;
import com.example.h2_shop.model.dto.ProductSearchResponse;
import org.springframework.data.domain.Page;

public interface ProductCustomeRepository {
    Page<ProductSearchResponse> searchProduct(ProductRequestDTO productRequestDTO);
}
