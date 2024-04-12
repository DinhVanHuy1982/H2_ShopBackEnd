package com.example.h2_shop.repository;

import com.example.h2_shop.model.Categories;
import com.example.h2_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    public Optional<Categories> findByCategoriCode(String code);

    Optional<Categories> findByParentId(Long parentId);
}
