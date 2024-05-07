package com.example.h2_shop.repository;

import com.example.h2_shop.model.ProductImg;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg,Long> {

    List<ProductImg> findByProductId(Long productId);


    List<ProductImg> findByOrderDetailId(Long orderDetailId);
}
