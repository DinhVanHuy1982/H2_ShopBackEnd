package com.example.h2_shop.repository;

import com.example.h2_shop.model.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface NotifyRepository extends JpaRepository<Notify,Long> {
}
