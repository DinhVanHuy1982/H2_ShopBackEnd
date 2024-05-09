package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.SaleDTO;
import com.example.h2_shop.model.dto.SaleSearchResponseDTO;
import com.example.h2_shop.service.SaleService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SaleController {

    @Autowired
    SaleService saleService;

    public SaleController() {
    }

    @PostMapping("/create/sale")
    public ServiceResult<SaleDTO> createSale(@RequestBody SaleDTO saleDTO){
        return this.saleService.createSale(saleDTO);
    }
    @PostMapping("/search/sale")
    public ServiceResult<Page<SaleSearchResponseDTO>> searchSale(@RequestBody SaleSearchResponseDTO saleSearchResponseDTO){
        return this.saleService.searchSeal(saleSearchResponseDTO);
    }

    @GetMapping("/search-detail/sale/{code}")
    public ServiceResult<SaleSearchResponseDTO> searchSale(@PathVariable String code){
        return this.saleService.getDetailSale(code);
    }

    @PostMapping("/update/sale")
    public ServiceResult<SaleDTO> updateSale(@RequestBody SaleDTO saleDTO){
        return this.saleService.updateSale(saleDTO);
    }
}
