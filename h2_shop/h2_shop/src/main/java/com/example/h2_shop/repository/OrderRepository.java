package com.example.h2_shop.repository;

import com.example.h2_shop.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {


    @Query(value = "   SELECT \n" +
            "    o.id,\n" +
            "    u.id AS userId,\n" +
            "    u.full_name AS userBuy,\n" +
            "    o.order_code AS code,\n" +
            "    o.price AS priceOrder,\n" +
            "    o.order_date AS orderDate,\n" +
            "    o.status,\n" +
            "    o.payment_method paymentMethod,\n" +
            "    a.name nameStatus\n" +
            "FROM \n" +
            "    orders o \n" +
            "JOIN \n" +
            "    `user` u ON u.id = o.user_id\n" +
            "join (select * from app_params ap where  ap.type = 'ORDERSTATUS') a on a.value = o.status  \n" +
            "  where (:status is null or :status = o.status) \n" +
            "  and (:keySearch is null or o.order_code like concat('%',:keySearch,'%'))",nativeQuery = true)
    Page<Map<String,Object>> searchOrder(Pageable pageable, @Param("status") Long status, @Param("keySearch")String keySearch);


    /**
     * lấy thông tin chung của đơn đặt hàng
     *
     * @param order_id
     * @return
     * @throws
     * @author admin
     * @since 5/11/2024
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0
     */
    @Query(value = "SELECT\n" +
            "    o.id,\n" +
            "    o.order_code AS code,\n" +
            "    o.full_name AS fullName,\n" +
            "    o.price,\n" +
            "    o.payment_method paymentMethod,\n" +
            "    o.ship_price shipPrice,\n" +
            "    o.phone_number AS phoneNumber,\n" +
            "    o.province_id AS provinceId,\n" +
            "    o.district_id AS districtId,\n" +
            "    o.ward,\n" +
            "    o.shipping_unit AS shippingUnit,\n" +
            "    o.pay_status AS payStatus,\n" +
            "    o.status,\n" +
            "    IF(\n" +
            "        s2.id IS NULL,\n" +
            "        \"\",\n" +
            "        IF(\n" +
            "            NOW() BETWEEN s2.start_time AND s2.end_time,\n" +
            "            IF(\n" +
            "                s2.quantity > 0,\n" +
            "                s2.name,\n" +
            "                CONCAT(s2.name, ' (Đã hết mã giảm giá)')\n" +
            "            ),\n" +
            "            CONCAT(s2.name, ' (Đã quá hạn)')\n" +
            "        )\n" +
            "    ) AS saleName,\n" +
            "    IF(\n" +
            "        s2.id IS NULL,\n" +
            "        0,\n" +
            "        IF(\n" +
            "            NOW() BETWEEN s2.start_time AND s2.end_time,\n" +
            "            IF(\n" +
            "                s2.quantity > 0,\n" +
            "                1,\n" +
            "                2\n" +
            "            ),\n" +
            "            3\n" +
            "        )\n" +
            "    ) AS typeSale,\n" +
            "    GROUP_CONCAT(od.id SEPARATOR ',') AS orderDetailConcat\n" +
            "FROM\n" +
            "    orders o\n" +
            "JOIN (\n" +
            "    SELECT\n" +
            "        ap.value,\n" +
            "        ap.name\n" +
            "    FROM\n" +
            "        app_params ap\n" +
            "    WHERE\n" +
            "        ap.type = 'ORDERSTATUS'\n" +
            ") ap ON ap.value = o.status\n" +
            "LEFT JOIN (\n" +
            "    SELECT\n" +
            "        ap.value,\n" +
            "        ap.name\n" +
            "    FROM\n" +
            "        app_params ap\n" +
            "    WHERE\n" +
            "        ap.type = 'PAYSTATUS'\n" +
            ") ap2 ON ap2.value = o.pay_status\n" +
            "JOIN order_detail od ON o.id = od.order_id\n" +
            "LEFT JOIN sales s2 ON s2.id = o.sale_id\n" +
            "WHERE\n" +
            "    o.id = :orderId", nativeQuery = true)
    Map<String,Object> detailOrder(@Param("orderId")Long id);


    /**
     * Lấy ra danh sách order_detail thuộc chuỗi order_id ( 2,34,5) truyền vào
     *
     * @param orderDetailIdlst
     * @return
     * @throws
     * @author dinh van huy
     * @since 5/11/2024
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0
     */
    @Query(value = "SELECT\n" +
            "    od.id,\n" +
            "    p.product_name AS productName,\n" +
            "    od.quantity,\n" +
            "    od.price,\n" +
            "    IF(sale.id IS NULL, NULL, od.price * (100 - sale.max_purchase) / 100) AS priceSale,\n" +
            "    s.size_name sizeName,\n" +
            "    tp.type_name typeName,\n" +
            "    pd.quantity quantityHave,\n" +
            "    pd.id as productDetailId\n" +
            "FROM\n" +
            "    order_detail od\n" +
            "LEFT JOIN product_detail pd ON pd.id = od.product_detail_id \n" +
            "LEFT JOIN type_product tp ON tp.id = pd.type_product_id \n" +
            "LEFT JOIN sizes s ON s.id = pd.size_id \n" +
            "LEFT JOIN products p ON p.id = pd.product_id \n" +
            "LEFT JOIN (\n" +
            "    SELECT * FROM sales s2 WHERE NOW() BETWEEN s2.start_time AND s2.end_time AND type = 0\n" +
            ") AS sale ON sale.product_id = p.id\n" +
            "WHERE\n" +
            "    FIND_IN_SET(od.id, :orderDetailIdConcat) > 0;\n",nativeQuery = true)
    List<Map<String,Object>> getListOrderDetailOfOrder(@Param("orderDetailIdConcat")String orderDetailIdlst);

    List<Orders> findByUserId(Long userId);
}
