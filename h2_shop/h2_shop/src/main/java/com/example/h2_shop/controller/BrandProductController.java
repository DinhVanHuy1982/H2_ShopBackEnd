package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.ProductDetailResponseDTO;
import com.example.h2_shop.service.BrandProductService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BrandProductController {


    @Autowired
    BrandProductService brandProductService;

    @PostMapping("/import/product")
    ServiceResult<?> importProduct(@RequestBody ProductDetailResponseDTO productDetailResponseDTO ){

        return this.brandProductService.importProduct(productDetailResponseDTO);
    }

    @GetMapping("/import/get-history/{id}")
    ServiceResult<?> historyImportProduct(@PathVariable Long id){

        return this.brandProductService.getHistoryImport(id);
    }
}
