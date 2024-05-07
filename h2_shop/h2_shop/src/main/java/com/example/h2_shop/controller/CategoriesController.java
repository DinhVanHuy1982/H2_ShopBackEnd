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

    @PostMapping("/getCategories")
    public ServiceResult<List<CategoriesDTO>> getAllCategoriesTree(@RequestBody CategoriesDTO categoriesDTO){
        return this.categoriesService.getTreeCategories(categoriesDTO);
    }

    @GetMapping("/get-categories-no-tree")
    public ServiceResult<List<CategoriesDTO>> getNoTree(){
        return this.categoriesService.getNoTreeCategories();
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

    @PostMapping("/update-categories")
    public ServiceResult<CategoriesDTO> updateCategories(@RequestBody CategoriesDTO categoriesDTO){
        return this.categoriesService.updateCategories(categoriesDTO);
    }
    @GetMapping("/get-by-id/{id}")
    public ServiceResult<CategoriesDTO> getById(@PathVariable Long id){
        return  this.categoriesService.getById(id);
    }
}
