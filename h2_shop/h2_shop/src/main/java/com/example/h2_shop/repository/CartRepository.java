package com.example.h2_shop.repository;

import com.example.h2_shop.model.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Carts,Long> {

    @Query(value = "select * from carts where user_id = ?1 and product_detail_id = ?2 ",nativeQuery = true)
    public Carts findByUserIdAndProductDetail(Long userId, Long productDetailId);
}
