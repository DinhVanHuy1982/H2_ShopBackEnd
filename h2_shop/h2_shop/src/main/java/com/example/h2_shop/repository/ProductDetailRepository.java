package com.example.h2_shop.repository;

import com.example.h2_shop.model.ProductDetail;
import com.example.h2_shop.model.dto.ProductDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    List<ProductDetail> findAllByProductId(Long productId);

    @Query(value = "select * from product_detail pd where pd.type_product_id = ?1  and pd.size_id = ?2 and pd.product_id = ?3",nativeQuery = true)
    Optional<ProductDetail> findByTypeIdSizeIdProductId(Long typeId, Long sizeId, Long productId );

    @Query(value = "select * from product_detail pd where (?1 is null or pd.type_product_id = ?1)  and ( ?2 is null or pd.size_id = ?2) and (?3 is null or pd.product_id = ?3)",nativeQuery = true)
    Optional<ProductDetail> findByTypeIdSizeIdProductIdWithNull(Long typeId, Long sizeId, Long productId );
}
