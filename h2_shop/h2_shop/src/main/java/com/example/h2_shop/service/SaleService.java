package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.SaleDTO;
import com.example.h2_shop.model.dto.SaleSearchResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SaleService {
    ServiceResult<SaleDTO> createSale(SaleDTO saleDTO);

    ServiceResult<Page<SaleSearchResponseDTO>> searchSeal(SaleSearchResponseDTO saleSearchResponseDTO);

    ServiceResult<SaleSearchResponseDTO> getDetailSale(String code);
    ServiceResult<SaleDTO> updateSale(SaleDTO saleDTO);
    ServiceResult<List<SaleDTO>>  searchSaleBill();
}
