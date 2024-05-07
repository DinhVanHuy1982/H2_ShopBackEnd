package com.example.h2_shop.repository;

import com.example.h2_shop.model.Categories;
import com.example.h2_shop.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    public Optional<Categories> findByCategoriCode(String code);

    Optional<Categories> findByParentId(Long parentId);

    List<Categories> findAllByOrderByCategoriCode();

}
