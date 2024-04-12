package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.BrandsDTO;
import com.example.h2_shop.service.BrandService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    private final BrandService brandService;
    public BrandController(BrandService brandService){
        this.brandService=brandService;
    }

    @PostMapping("/createBrand")
    public ServiceResult<BrandsDTO> createBrand(@RequestPart(value = "avatar",required = false)MultipartFile avatar, @RequestPart("brand") BrandsDTO brandsDTO){
        return this.brandService.createBrand(avatar,brandsDTO);
    }

    @PostMapping("/getPageBrand")
    public Page<BrandsDTO> getPageBrand(@RequestBody BrandsDTO brandsDTO,
                                              @RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                                              @RequestParam(value = "size",required = false,defaultValue = "10")Integer size

    ){
        Pageable pageable = PageRequest.of(page-1, size);

//        Page<BrandsDTO> serviceResult = this.brandService.getBrandWithPage(brandsDTO, pageable);

        return this.brandService.getBrandWithPage(brandsDTO,pageable);
    }


}
