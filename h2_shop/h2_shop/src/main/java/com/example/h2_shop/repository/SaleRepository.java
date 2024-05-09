package com.example.h2_shop.repository;

import com.example.h2_shop.model.Product;
import com.example.h2_shop.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {

    @Query(value = "select * from sales s where product_id = ?1 and (s.start_time <= now() and s.end_time>= now()) and type=0",nativeQuery = true)
    public Optional<Sale> findByProductID(Long productId);

    @Query(value = "select * from sales s where (s.start_time <= now() and s.end_time>= now()) and type=1",nativeQuery = true)
    public Sale findByTypeAndTime();

    @Query(value = "select  s.code, s.name,s.`type`, s.quantity , s.start_time startTime , s.end_time endTime, GROUP_CONCAT(s.product_id SEPARATOR ',') lstProductStr\n" +
            "from sales s \n" +
            "where (:type = '' or :type is null or :type=s.`type`) \n" +
            "and (:search ='' or :search is null or s.code like concat('%',:search,'%') or s.name like concat('%',:search,'%'))\n" +
            "and (:time=''  or :time is null or :time is null or (:time between s.start_time and s.end_time))\n" +
            "group by s.code, s.name,s.`type` , s.start_time , s.end_time \n" +
            "order by s.code", nativeQuery = true)
    Page<Map<String,Object>> searchSale(Pageable pageable, @Param("type")String type , @Param("search")String search, @Param("time")Instant time);


    //Lấy ra danh sách các bản ghi trùng ngày giảm giá
    @Query(value = "SELECT * \n" +
            "FROM sales s \n" +
            "WHERE (\n" +
            "    (:startTime BETWEEN s.start_time AND s.end_time)\n" +
            "    OR (:endTime BETWEEN s.start_time AND s.end_time)\n" +
            "    OR (s.start_time BETWEEN :startTime AND :endTime)\n" +
            "    OR (s.end_time BETWEEN :startTime AND :endTime)\n" +
            "    )\n" +
            "    AND FIND_IN_SET(s.product_id, REPLACE(:lstProductId, \" \", \"\")) > 0", nativeQuery = true)
    List<Sale> findDupplicateSale(@Param("startTime")Instant startTime, @Param("endTime")Instant endTime, @Param("lstProductId")String lstProductId);

    List<Sale> findByCode(String code);

    @Query(value = "select s.code, s.name,s.`type` , s.start_time startTime, s.end_time endTime, s.description , s.quantity ,s.max_purchase maxPurchase,GROUP_CONCAT(s.product_id SEPARATOR ',') lstProductStr\n" +
            "from sales s \n" +
            "where s.code = :code\n" +
            "group by s.code", nativeQuery = true)
    Map<String,Object> detailSaleByCode(@Param("code")String code);

    @Modifying
    void deleteByCode(String code);
}
