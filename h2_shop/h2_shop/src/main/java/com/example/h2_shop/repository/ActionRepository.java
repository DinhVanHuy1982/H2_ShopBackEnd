package com.example.h2_shop.repository;

import com.example.h2_shop.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action,Long> {
}
