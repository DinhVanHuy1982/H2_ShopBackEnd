package com.example.h2_shop.repository;

import com.example.h2_shop.model.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface CartRepository extends JpaRepository<Carts,Long> {

    @Query(value = "select * from carts where user_id = ?1 and product_detail_id = ?2 ",nativeQuery = true)
    public Carts findByUserIdAndProductDetail(Long userId, Long productDetailId);



    @Query(value = "SELECT\n" +
            "    c.id,\n" +
            "    c.quantity,\n" +
            "    s.size_name AS sizeProductName,\n" +
            "    tp.type_name AS typeProductName,\n" +
            "    s.id AS sizeProductId,\n" +
            "    tp.id AS typeProductId,\n" +
            "    pd.quantity AS residualQuantity,\n" +
            "    pd.product_id AS productId,\n" +
            "    p.product_name AS productName,\n" +
            "    p.price AS price,\n" +
            "    pi2.file_name AS fileName\n" +
            "FROM\n" +
            "    carts c\n" +
            "JOIN product_detail pd ON\n" +
            "    pd.id = c.product_detail_id\n" +
            "JOIN sizes s ON\n" +
            "    pd.size_id = s.id\n" +
            "JOIN type_product tp ON\n" +
            "    tp.id = pd.type_product_id\n" +
            "JOIN products p ON\n" +
            "    p.id = pd.product_id\n" +
            "LEFT JOIN product_img pi2 ON\n" +
            "    pi2.product_id = p.id\n" +
            "WHERE\n" +
            "    c.user_id = :userId\n" +
            "    AND pi2.avatar = true",nativeQuery = true)
    List<Map<String, Object>> getAllCartOfUser(@Param("userId") Long userId);
}
