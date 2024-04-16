package com.example.h2_shop.repository;

import com.example.h2_shop.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
