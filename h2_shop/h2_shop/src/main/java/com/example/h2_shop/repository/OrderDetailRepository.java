package com.example.h2_shop.repository;

import com.example.h2_shop.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

    @Query(value = "select od.*  from `user` u join orders o on o.user_id =u.id \n" +
            "right join order_detail od on o.id =od.order_id \n" +
            "join product_detail pd on pd.id = od.product_detail_id \n" +
            "join products p on pd.product_id =p.id \n" +
            "where u.id = ?1 and p.id= ?2 and o.status = 3\n" +
            " and (?3 is null or ?3=od.id)" +
            "order by od.order_id asc " +
            "limit 1", nativeQuery = true)
    public OrderDetail findListOrderCompleteByProductAndUser(Long userId, Long productId,Long orderDetailId);

    @Query(value = "SELECT od.* FROM product_detail pd \n" +
            "   RIGHT JOIN order_detail od ON od.product_detail_id = pd.id \n" +
            "   left join orders o on o.id = od.order_id \n" +
            "   WHERE pd.product_id = :productId\n" +
            "   and o.status =3", nativeQuery = true)
    List<OrderDetail> getOrderByIdProduct(@Param("productId") Long productId);

    @Query(value = "SELECT od.*\n" +
            "FROM order_detail od\n" +
            "LEFT JOIN orders o ON o.id = od.order_id\n" +
            "LEFT JOIN `user` u ON u.id = o.user_id\n" +
            "RIGHT JOIN product_detail pd ON od.product_detail_id = pd.id\n" +
            "RIGHT JOIN products p ON p.id = pd.product_id\n" +
            "WHERE u.id = :userId\n" +
            "  AND p.id = :productId\n" +
            "  AND o.status = 3\n" +
            "  AND od.comment IS NULL\n" +
            "  AND od.rating IS NULL\n" +
            "ORDER BY o.order_date DESC\n" +
            "LIMIT 1;\n", nativeQuery = true)
    Optional<OrderDetail> checkAllowComment(@Param("userId") Long userId, @Param("productId") Long productId);
    @Query(value = "SELECT \n" +
            "    u.id AS userId,\n" +
            "    u.full_name AS fullName,\n" +
            "    u.avatar AS avatar,\n" +
            "    od.rating,\n" +
            "    od.comment,\n" +
            "    od.reply_comment commentRepply\n" +
            "FROM \n" +
            "    order_detail od \n" +
            "JOIN \n" +
            "    orders o ON o.id = od.order_id \n" +
            "JOIN \n" +
            "    `user` u ON u.id = o.user_id \n" +
            "WHERE \n" +
            "    od.id = :orderDetailId \n" +
            "    AND od.rating >= 1", nativeQuery = true)
    Map<String,Object> detaiCommentByUser(@Param("orderDetailId") Long orderDetailId);

    @Query(value = "SELECT \n" +
            "    pi2.file_name,\n" +
            "    pi2.`type`\n" +
            "FROM \n" +
            "    order_detail od \n" +
            "JOIN \n" +
            "    orders o ON od.order_id = o.id \n" +
            "JOIN \n" +
            "    product_img pi2 ON pi2.order_detail_id = od.id \n" +
            "WHERE \n" +
            "    od.id = :orderDetailId", nativeQuery = true)
    List<Map<String,Object>> getImgCommentAndRepply(@Param("orderDetailId") Long ordeDetailId);



}
