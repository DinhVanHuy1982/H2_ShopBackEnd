package com.example.h2_shop.repository;

import com.example.h2_shop.model.dto.CategoriesDTO;

import java.util.List;


public interface CategoriesRepositoryCustome {
    public List<CategoriesDTO> getAllCategories(CategoriesDTO categoriesDTO);
    public List<CategoriesDTO> getAllCategoriesActive();
}
