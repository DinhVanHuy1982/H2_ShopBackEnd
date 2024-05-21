package com.example.h2_shop.repository;

import com.example.h2_shop.model.OrderDetail;
import com.example.h2_shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findByProductCode(String code);
    List<Product> findAllByOrderByProductCodeAsc();

    @Query(value = "SELECT \n" +
            "    p.id,\n" +
            "    p.product_code AS productCode,\n" +
            "    p.product_name AS productName,\n" +
            "    p.price,\n" +
            "    p.create_time AS createTime,\n" +
            "    b.brand_name AS brandName,\n" +
            "    b.id AS brandId,\n" +
            "    c.categori_name AS categoriesName,\n" +
            "    c.id AS categoriesId,\n" +
            "    SUM(pd.quantity) AS quantity\n" +
            "FROM \n" +
            "    products p\n" +
            "LEFT JOIN \n" +
            "    product_detail pd ON pd.product_id = p.id\n" +
            "LEFT JOIN \n" +
            "    (\n" +
            "        SELECT DISTINCT \n" +
            "            b.brand_name, \n" +
            "            b.id, \n" +
            "            bp.product_id  \n" +
            "        FROM \n" +
            "            brand b \n" +
            "        LEFT JOIN \n" +
            "            brand_product bp ON bp.brand_id = b.id\n" +
            "    ) b ON b.product_id = p.id\n" +
            "JOIN \n" +
            "    categories c ON c.id = p.categories_id\n" +
            "WHERE \n" +
            "    (:keySearch IS NULL OR p.product_code LIKE CONCAT('%', :keySearch, '%') OR p.product_name LIKE CONCAT('%', :keySearch, '%'))\n" +
            "    AND (:brandId IS NULL OR b.id = :brandId)\n" +
            "    AND (:categoriId IS NULL OR c.id = :categoriId)\n" +
            "GROUP BY \n" +
            "    p.id,\n" +
            "    p.product_code,\n" +
            "    p.product_name,\n" +
            "    p.price,\n" +
            "    p.create_time,\n" +
            "    b.brand_name,\n" +
            "    b.id,\n" +
            "    c.categori_name,\n" +
            "    c.id;\n", nativeQuery = true)
    public Page<Map<String,Object>> getPageProductView(Pageable pageable, @Param("keySearch")String search, @Param("brandId")Long brandId, @Param("categoriId") Long categoriId);

    @Query(value = "SELECT *\n" +
            "FROM (\n" +
            "    SELECT\n" +
            "        DISTINCT p.id,\n" +
            "        pi.file_name AS avatar,\n" +
            "        p.product_name AS productName,\n" +
            "        p.product_code AS productCode,\n" +
            "        p.price AS price,\n" +
            "        (p.price * (100 - s.max_purchase))/100 AS priceSale,\n" +
            "        c.categori_code AS categoriCode,\n" +
            "        c.categori_name AS categorieName,\n" +
            "        SUM(pd.quantity) AS quantityHave,\n" +
            "        s.max_purchase maxPurchase\n" +
            "    FROM\n" +
            "        products p\n" +
            "    LEFT JOIN product_detail pd ON pd.product_id = p.id\n" +
            "    LEFT JOIN categories c ON c.id = p.categories_id\n" +
            "    LEFT JOIN sales s ON s.product_id = p.id\n" +
            "    LEFT JOIN product_img pi ON pi.product_id = p.id AND pi.avatar = 1\n" +
            "    GROUP BY\n" +
            "        p.id,\n" +
            "        pi.file_name,\n" +
            "        p.product_name,\n" +
            "        p.product_code,\n" +
            "        p.price,\n" +
            "        c.categori_code,\n" +
            "        c.categori_name\n" +
            "    LIMIT 15\n" +
            ") AS tb1\n" +
            "left JOIN (\n" +
            "    SELECT\n" +
            "        p.id AS productId,\n" +
            "        IF(SUM(od.quantity) IS NULL, 0, SUM(od.quantity)) AS orderQuantity,\n" +
            "        IF(AVG(od.rating) IS NULL, 0, AVG(od.rating)) AS ratingAvg\n" +
            "    FROM\n" +
            "        products p\n" +
            "    LEFT JOIN product_detail pd ON pd.product_id = p.id\n" +
            "    LEFT JOIN order_detail od ON od.product_detail_id = pd.id\n" +
            "    LEFT JOIN categories c ON c.id = p.categories_id\n" +
            "    left join orders o on od.order_id = o.id\n" +
            "    where o.status=3\n" +
            "    GROUP BY\n" +
            "        p.id\n" +
            "    LIMIT 15\n" +
            ") AS tb2 ON tb2.productId = tb1.id\n" +
            "ORDER BY\n" +
            "    tb2.orderQuantity DESC,\n" +
            "    tb1.quantityHave DESC,\n" +
            "    tb2.ratingAvg DESC", nativeQuery = true)
    public List<Map<String,Object>> getListBestSeller();


    @Query(value = "SELECT \n" +
            "    pd.product_id AS id,\n" +
            "    AVG(od.rating) AS avgRate,\n" +
            "    SUM(CASE WHEN od.rating = 1 THEN 1 ELSE 0 END) AS rate1,\n" +
            "    SUM(CASE WHEN od.rating = 2 THEN 1 ELSE 0 END) AS rate2,\n" +
            "    SUM(CASE WHEN od.rating = 3 THEN 1 ELSE 0 END) AS rate3,\n" +
            "    SUM(CASE WHEN od.rating = 4 THEN 1 ELSE 0 END) AS rate4,\n" +
            "    SUM(CASE WHEN od.rating = 5 THEN 1 ELSE 0 END) AS rate5,\n" +
            "    COUNT(od.rating) AS totalRate\n" +
            "FROM \n" +
            "    product_detail pd\n" +
            "LEFT JOIN \n" +
            "    order_detail od ON od.product_detail_id = pd.id\n" +
            "WHERE \n" +
            "    pd.product_id = :productId",nativeQuery = true)
    Map<String,Object> getInforCommentProduct(@Param("productId") Long id);

    @Query(value = "select od.* from products p left join product_detail pd on pd.product_id =p.id\n" +
            "    right join order_detail od on od.product_detail_id =pd.id \n" +
            "    where p.id =:productId", nativeQuery = true)
    List<Map<String,Object>> getOrderOfProduct(@Param("productId")Long id);

    @Query(value = "select * from products where categories_id =:categoriId",nativeQuery = true)
    List<Product> getProductByCategoriId(@Param("categoriId")Long id);


    @Query(value = "SELECT\n" +
            "    *\n" +
            "FROM\n" +
            "    (\n" +
            "        SELECT\n" +
            "            p.id AS id,\n" +
            "            p.price,\n" +
            "            (p.price * ((100 - s.max_purchase) / 100)) AS priceSale,\n" +
            "            p.product_name AS productName,\n" +
            "            s.max_purchase AS maxPurchase,\n" +
            "            SUM(pd.quantity) AS quantityHave,\n" +
            "            pi.file_name AS avatar,\n" +
            "            c.categori_code AS categoriCode,\n" +
            "            c.categori_name AS categorieName\n" +
            "        FROM\n" +
            "            sales s\n" +
            "        RIGHT JOIN products p ON p.id = s.product_id\n" +
            "        LEFT JOIN categories c ON c.id = p.categories_id\n" +
            "        LEFT JOIN product_detail pd ON pd.product_id = p.id\n" +
            "        LEFT JOIN product_img pi ON pi.product_id = p.id AND pi.avatar = 1\n" +
            "        WHERE\n" +
            "            (NOW() BETWEEN s.start_time AND s.end_time)\n" +
            "            AND s.`type` = 0\n" +
            "            AND s.code = :saleCode\n" +
            "        GROUP BY\n" +
            "            p.id\n" +
            "    ) AS tbl1\n" +
            "LEFT JOIN \n" +
            "    (\n" +
            "        SELECT\n" +
            "            p.id AS productId,\n" +
            "            IF(SUM(od.quantity) IS NULL, 0, SUM(od.quantity)) AS orderQuantity,\n" +
            "            IF(AVG(od.rating) IS NULL, 0, AVG(od.rating)) AS ratingAvg\n" +
            "        FROM\n" +
            "            products p\n" +
            "        LEFT JOIN product_detail pd ON pd.product_id = p.id\n" +
            "        LEFT JOIN order_detail od ON od.product_detail_id = pd.id\n" +
            "        LEFT JOIN categories c ON c.id = p.categories_id\n" +
            "        LEFT JOIN orders o ON od.order_id = o.id\n" +
            "        WHERE\n" +
            "            o.status = 3\n" +
            "        GROUP BY\n" +
            "            p.id\n" +
            "    ) AS tbl2 \n" +
            "ON\n" +
            "    tbl2.productId = tbl1.id\n", nativeQuery = true)
    List<Map<String,Object>> getLstProductSaleForSaleCode(@Param("saleCode")String saleCode);
}
