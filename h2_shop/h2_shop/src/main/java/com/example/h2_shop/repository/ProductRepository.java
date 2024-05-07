package com.example.h2_shop.repository;

import com.example.h2_shop.model.OrderDetail;
import com.example.h2_shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findByProductCode(String code);

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
            "    brand_product bp ON bp.product_id = p.id\n" +
            "LEFT JOIN \n" +
            "    brand b ON bp.brand_id = b.id\n" +
            "JOIN \n" +
            "    categories c ON c.id = p.categories_id\n" +
            "WHERE \n" +
            "    (:keySearch IS NULL OR p.product_code LIKE CONCAT('%', :keySearch, '%') OR p.product_name LIKE CONCAT('%', :keySearch, '%'))\n" +
            "    AND (:brandId IS NULL OR b.id = :brandId)\n" +
            "    AND (:categoriId IS NULL OR c.id = :categoriId)\n" +
            "GROUP BY \n" +
            "    p.id", nativeQuery = true)
    public Page<Map<String,Object>> getPageProductView(Pageable pageable, @Param("keySearch")String search, @Param("brandId")Long brandId, @Param("categoriId") Long categoriId);

    @Query(value = "SELECT *\n" +
            "FROM (\n" +
            "    SELECT\n" +
            "        DISTINCT p.id," +
            "        pi.file_name avatar,\n" +
            "        p.product_name AS productName,\n" +
            "        p.product_code AS productCode,\n" +
            "        p.price AS price,\n" +
            "        (p.price * (1 - s.max_purchase)) AS priceSale,\n" +
            "        c.categori_code AS categoriCode,\n" +
            "        c.categori_name AS categoriName,\n" +
            "        SUM(pd.quantity) AS quantityHave\n" +
            "    FROM\n" +
            "        products p\n" +
            "    LEFT JOIN product_detail pd ON pd.product_id = p.id\n" +
            "    LEFT JOIN categories c ON c.id = p.categories_id\n" +
            "    LEFT JOIN sales s ON s.product_id = p.id\n" +
            "    left join product_img pi on pi.product_id = p.id and pi.avatar=1" +
            "    GROUP BY\n" +
            "        p.id,\n" +
            "        p.product_name,\n" +
            "        p.product_code,\n" +
            "        p.price,\n" +
            "        c.categori_code,\n" +
            "        c.categori_name\n" +
            "    LIMIT 15\n" +
            ") AS tb1\n" +
            "JOIN (\n" +
            "    SELECT\n" +
            "        p.id AS productId,\n" +
            "        IF(SUM(od.quantity) IS NULL, 0, SUM(od.quantity)) AS orderQuantity,\n" +
            "        IF(AVG(od.rating) IS NULL, 0, AVG(od.rating)) AS ratingAvg\n" +
            "    FROM\n" +
            "        products p\n" +
            "    LEFT JOIN product_detail pd ON pd.product_id = p.id\n" +
            "    LEFT JOIN order_detail od ON od.product_detail_id = pd.id\n" +
            "    LEFT JOIN categories c ON c.id = p.categories_id\n" +
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

}
