package com.example.h2_shop.repository;

import com.example.h2_shop.model.BrandProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BrandProductRepository extends JpaRepository<BrandProduct,Long> {

    @Query(value = "SELECT \n" +
            "    p.product_name AS productName,\n" +
            "    b.brand_name AS brandName,\n" +
            "    bp.import_date AS importDate,\n" +
            "    bp.import_price AS importPrice,\n" +
            "    bp.import_quantity AS importQuantity,\n" +
            "    SUM(bp.import_quantity) AS totalImport\n" +
            "FROM \n" +
            "    brand_product bp\n" +
            "LEFT JOIN \n" +
            "    products p ON p.id = bp.product_id\n" +
            "LEFT JOIN \n" +
            "    brand b ON b.id = bp.brand_id\n" +
            "WHERE \n" +
            "    bp.product_id = :productId\n" +
            "GROUP BY \n" +
            "    p.product_name, \n" +
            "    b.brand_name, \n" +
            "    bp.import_date\n",nativeQuery = true)
    List<Map<String,Object>> getHistoryImportByProductId(@Param("productId")Long productId);

    @Query(value = "select bp.* from brand_product bp where bp.brand_id =:brandId",nativeQuery = true)
    List<BrandProduct> findByBrandId(@Param("brandId") Long brandId);
    @Query(value = "SELECT if(SUM(bp.import_quantity) is null, 0,SUM(bp.import_quantity)) FROM brand_product bp WHERE bp.product_id = :productId", nativeQuery = true)
    Long sumQuantityByProduct(@Param("productId") Long productId);

    void deleteByProductId(Long productId);

    List<BrandProduct> findByProductId(Long id);
}
