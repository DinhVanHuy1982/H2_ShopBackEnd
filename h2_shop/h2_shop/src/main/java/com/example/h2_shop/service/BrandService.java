package com.example.h2_shop.service;

import com.example.h2_shop.model.Brands;
import com.example.h2_shop.model.dto.BrandsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface BrandService {

    public ServiceResult<BrandsDTO> createBrand(MultipartFile avatar, BrandsDTO brandsDTO);
    public Page<BrandsDTO> getBrandWithPage(BrandsDTO brandsDTO, Pageable pageable);
}
