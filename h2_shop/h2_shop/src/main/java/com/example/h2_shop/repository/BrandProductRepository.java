package com.example.h2_shop.repository;

import com.example.h2_shop.model.BrandProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandProductRepository extends JpaRepository<BrandProduct,Long> {
}
