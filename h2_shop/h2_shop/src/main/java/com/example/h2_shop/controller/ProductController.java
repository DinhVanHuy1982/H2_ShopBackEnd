package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.ProductDTO;
import com.example.h2_shop.service.ProductService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @PostMapping("/createProduct")
    public ResponseEntity<ServiceResult<?>> createProduct(@RequestPart(value = "file",required = false) List<MultipartFile> fileAvatar ){
        ServiceResult<?> stringServiceResult = this.productService.createService(fileAvatar);
        return ResponseEntity.ok(stringServiceResult);
    }
}
