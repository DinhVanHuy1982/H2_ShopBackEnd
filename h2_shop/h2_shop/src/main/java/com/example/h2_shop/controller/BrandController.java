package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.BrandsDTO;
import com.example.h2_shop.service.BrandService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BrandController {
    private final BrandService brandService;
    public BrandController(BrandService brandService){
        this.brandService=brandService;
    }

    @PostMapping("/brand/createBrand")
    public ServiceResult<BrandsDTO> createBrand(@RequestPart(value = "avatar",required = false)MultipartFile avatar, @RequestPart("brand") BrandsDTO brandsDTO){
        return this.brandService.createBrand(avatar,brandsDTO);
    }

    @PostMapping("/brand/updateBrand")
    public ServiceResult<BrandsDTO> updateBrand(@RequestPart(value = "avatar",required = false)MultipartFile avatar, @RequestPart("brand") BrandsDTO brandsDTO) throws IOException {
        return  this.brandService.updateBrand(avatar,brandsDTO);
    }

    @PostMapping("/brand/getPageBrand")
    public Page<BrandsDTO> getPageBrand(@RequestBody BrandsDTO brandsDTO,
                                              @RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                                              @RequestParam(value = "size",required = false,defaultValue = "10")Integer size

    ){
        Pageable pageable = PageRequest.of(page-1, size);
        return this.brandService.getBrandWithPage(brandsDTO,pageable);
    }

    @GetMapping("/brand/getListBrand")
    public List<BrandsDTO> getListBrand(){
        return this.brandService.getListBrand();
    }

    @GetMapping("/brand/detail-brand/{id}")
    public ServiceResult<BrandsDTO> detailBrand(@PathVariable("id")Long id){

        return this.brandService.detailBrandById(id);
    }

}
