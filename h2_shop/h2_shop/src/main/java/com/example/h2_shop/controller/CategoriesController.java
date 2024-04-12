package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.CategoriesDTO;
import com.example.h2_shop.service.CategoriesService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    private final CategoriesService categoriesService;
    public CategoriesController(CategoriesService categoriesService){
        this.categoriesService=categoriesService;
    }

    @GetMapping("/getCategories")
    public ServiceResult<List<CategoriesDTO>> getAllCategoriesTree(){
        return this.categoriesService.getTreeCategories();
    }

    /**
     * Description of the method
     *
     * @param categoriesDTO
     * @return
     * @throws
     * @author admin
     * @since 4/9/2024
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0
     */
    @PostMapping("/createCategories")
    public ServiceResult<CategoriesDTO> createCategories(@RequestBody CategoriesDTO categoriesDTO){
        return this.categoriesService.createCategories(categoriesDTO);
    }
}
