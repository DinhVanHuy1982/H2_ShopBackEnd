package com.example.h2_shop.repository;

import com.example.h2_shop.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SizeRepository extends JpaRepository<Size,Long> {

    @Query(value = "select distinct s.* from sizes s join product_detail pd on pd.size_id = s.id where pd.product_id = :productId",nativeQuery = true)
    public List<Size> getSizeOfProduct(@Param("productId") Long productId);
}
