package com.example.h2_shop.repository;

import com.example.h2_shop.model.TypeProduct;
import com.example.h2_shop.model.dto.TypeProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TypeProductRepository extends JpaRepository<TypeProduct, Long> {
}
