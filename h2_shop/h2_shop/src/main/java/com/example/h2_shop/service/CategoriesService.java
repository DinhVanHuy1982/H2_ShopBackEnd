package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.CategoriesDTO;

import java.util.List;

public interface CategoriesService {
    public ServiceResult<List<CategoriesDTO>> getTreeCategories();

    public ServiceResult<CategoriesDTO> createCategories(CategoriesDTO categoriesDTO);

}
