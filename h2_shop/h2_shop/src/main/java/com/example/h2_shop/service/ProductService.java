package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public ServiceResult<?> createProduct(List<MultipartFile> listFileAvatar,ProductDTO productDTO);
    public ServiceResult<?> createComment(List<MultipartFile> listFileAvatar);
}
