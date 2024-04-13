package com.example.h2_shop.repository;

import com.example.h2_shop.model.Product;
import com.example.h2_shop.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {

    @Query(value = "select * from sales s where product_id = ?1 and (s.start_time <= now() and s.end_time>= now()) and type=0",nativeQuery = true)
    public Optional<Sale> findByProductID(Long productId);

    @Query(value = "select * from sales s where (s.start_time <= now() and s.end_time>= now()) and type=1",nativeQuery = true)
    public Sale findByTypeAndTime();


}
