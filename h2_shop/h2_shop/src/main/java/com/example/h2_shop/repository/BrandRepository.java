package com.example.h2_shop.repository;

import com.example.h2_shop.model.Brands;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brands,Long> {

    public Optional<Brands> findByBrandCode(String code);

    @Query(value = "select * from brand b \n" +
            "where ((b.brand_code  like concat('%',?1,'%') or ?1 is null) \n" +
            "or (b.brand_name like concat( '%',?1,'%') or ?1 is null) ) and (?2 = b.status or ?2 is null)",nativeQuery = true)
    public Page<Brands> searchBrand(Pageable pageable,String search,Long status);

    List<Brands> findByStatus(Long status);

}
