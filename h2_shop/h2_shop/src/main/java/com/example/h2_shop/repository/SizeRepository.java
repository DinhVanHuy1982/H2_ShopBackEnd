package com.example.h2_shop.repository;

import com.example.h2_shop.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SizeRepository extends JpaRepository<Size,Long> {
}
