package com.example.h2_shop.repository;

import com.example.h2_shop.model.Apprams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApparamsRepository extends JpaRepository<Apprams,Long> {
    List<Apprams> findByType(String type);
}
