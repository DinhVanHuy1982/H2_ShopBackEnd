package com.example.h2_shop.repository;

import com.example.h2_shop.model.RolesDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RolesDetailsRepository extends JpaRepository<RolesDetails,Long> {

//    public List<RolesDetails> saveAll(List<RolesDetails> list);

}
