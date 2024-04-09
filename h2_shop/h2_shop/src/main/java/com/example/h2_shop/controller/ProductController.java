package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.ProductDTO;
import com.example.h2_shop.service.ProductService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @PostMapping(value ="/createProduct", consumes = {"multipart/form-data"})
    public ResponseEntity<ServiceResult<?>> createProduct(@RequestPart(value = "file",required = false) List<MultipartFile> fileAvatar, @RequestPart(value="productDTO") ProductDTO productDTO){
        ServiceResult<?> stringServiceResult = this.productService.createProduct(fileAvatar, productDTO);
        return ResponseEntity.ok(stringServiceResult);
    }

    @PostMapping(value ="/comment", consumes = {"multipart/form-data"})
    public ResponseEntity<ServiceResult<?>> createComment(@RequestPart(value = "file",required = false) List<MultipartFile> fileAvatar){
        ServiceResult<?> stringServiceResult = this.productService.createComment(fileAvatar);
        return ResponseEntity.ok(stringServiceResult);
    }

//    @CrossOrigin(origins = "http://localhost:4211")
    @GetMapping(value ="/testAPI")
    public ResponseEntity<ServiceResult<?>> testAPI(){
        ServiceResult<?> stringServiceResult = new ServiceResult<>(new ArrayList<>(), HttpStatus.BAD_GATEWAY, "ok");
        return ResponseEntity.ok(stringServiceResult);
    }
}
