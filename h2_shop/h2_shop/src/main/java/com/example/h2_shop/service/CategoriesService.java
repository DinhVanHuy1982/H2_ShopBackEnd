package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.CategoriesDTO;

import java.util.List;

public interface CategoriesService {
    public ServiceResult<List<CategoriesDTO>> getTreeCategories(CategoriesDTO categoriesDTO);
    ServiceResult<List<CategoriesDTO>> getNoTreeCategories();

    public ServiceResult<CategoriesDTO> createCategories(CategoriesDTO categoriesDTO);
    public ServiceResult<CategoriesDTO> updateCategories(CategoriesDTO categoriesDTO);
    public ServiceResult<CategoriesDTO> getById(Long id);

}
