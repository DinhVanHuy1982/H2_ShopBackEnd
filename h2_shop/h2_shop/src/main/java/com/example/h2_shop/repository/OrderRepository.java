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
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {


    @Query(value = "  SELECT \n" +
            "    o.id,\n" +
            "    u.id AS userId,\n" +
            "    u.full_name AS userBuy,\n" +
            "    o.order_code AS code,\n" +
            "    o.price AS priceOrder,\n" +
            "    o.order_date AS orderDate,\n" +
            "    o.status,\n" +
            "    o.payment_method AS paymentMethod,\n" +
            "    a.name AS nameStatus\n" +
            "FROM \n" +
            "    orders o \n" +
            "JOIN \n" +
            "    `user` u ON u.id = o.user_id\n" +
            "JOIN \n" +
            "    (SELECT * FROM app_params ap WHERE ap.type = 'ORDERSTATUS') a ON a.value = o.status\n" +
            "WHERE \n" +
            "    (:status IS NULL OR :status = o.status)\n" +
            "    AND (:keySearch IS NULL OR o.order_code LIKE CONCAT('%', :keySearch, '%'))\n" +
            "ORDER BY \n" +
            "    o.order_date DESC;\n",nativeQuery = true)
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
            "    s.size_name AS sizeName,\n" +
            "    tp.type_name AS typeName,\n" +
            "    pd.quantity AS quantityHave,\n" +
            "    pd.id AS productDetailId\n" +
            "FROM\n" +
            "    order_detail od\n" +
            "LEFT JOIN orders o ON o.id = od.order_id\n" +
            "LEFT JOIN product_detail pd ON pd.id = od.product_detail_id\n" +
            "LEFT JOIN type_product tp ON tp.id = pd.type_product_id\n" +
            "LEFT JOIN sizes s ON s.id = pd.size_id\n" +
            "LEFT JOIN products p ON p.id = pd.product_id\n" +
            "LEFT JOIN (\n" +
            "    SELECT * \n" +
            "    FROM sales s2 \n" +
            "    WHERE type = 0\n" +
            ") AS sale ON sale.product_id = p.id AND (o.order_date BETWEEN sale.start_time AND sale.end_time)\n" +
            "WHERE\n" +
            "    FIND_IN_SET(od.id, ?1) > 0;\n\n",nativeQuery = true)
    List<Map<String,Object>> getListOrderDetailOfOrder(@Param("orderDetailIdConcat")String orderDetailIdlst);

    List<Orders> findByUserId(Long userId);
    Optional<Orders> findByOrderCode(String orderCode);

    @Query(value = "select * from orders o where o.sale_id = :saleId ",nativeQuery = true)
    List<Orders> findBySaleId(@Param("saleId") Long saleId);


    @Query(value = "select\n" +
            "\to.id," +
            "\to.order_code orderCode,\n" +
            "\to.order_date orderDate,\n" +
            "\to.phone_number phoneNumber,\n" +
            "\to.price price,\n" +
            "\to.ship_price shipPrice,\n" +
            "\to.status ,\n" +
            "\to.payment_method paymentMethod,\n" +
            "\to.pay_status payStatus\n" +
            "from\n" +
            "\torders o\n" +
            "left join `user` u on\n" +
            "\tu.id = o.user_id\n" +
            "where\n" +
            "\tu.id = :userId\n" +
            "\tand if(:type = 1,\n" +
            "\t(o.status = 0),\n" +
            "\t(if(:type = 2,\n" +
            "\to.status = 1\n" +
            "\t\tor o.status = 2,\n" +
            "\t\tif(:type = 3,\n" +
            "\t\to.status = 3\n" +
            "\t\t\tor o.status = 4,\n" +
            "\t\t\tfalse))))", nativeQuery = true)
    List<Map<String,Object>> getAllOrderShipping(@Param("userId")Long id, @Param("type")int type);
}
