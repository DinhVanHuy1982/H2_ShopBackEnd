package com.example.h2_shop.repository.customRepo.impl;

import com.example.h2_shop.commons.DataUtil;
import com.example.h2_shop.commons.ReflectorUtil;
import com.example.h2_shop.model.dto.ProductRequestDTO;
import com.example.h2_shop.model.dto.ProductSearchResponse;
import com.example.h2_shop.repository.customRepo.ProductCustomeRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ProductCustomeImplRepository implements ProductCustomeRepository {

    @Autowired
    EntityManager entityManager;

    public ProductCustomeImplRepository() {
    }


    @Override
    public Page<ProductSearchResponse> searchProduct(ProductRequestDTO productRequestDTO) {
        Pageable pageable = PageRequest.of(productRequestDTO.getPage() -1,productRequestDTO.getPageSize());

        StringBuilder sql = new StringBuilder();
        StringBuilder sqlCount = new StringBuilder();
        sqlCount.append("SELECT \n" +
                "    p.id\n" +
                "FROM\n" +
                "    products p\n" +
                "JOIN product_detail pd ON pd.product_id = p.id\n" +
                "LEFT JOIN product_img pi2 ON pi2.product_id = p.id\n" +
                "LEFT JOIN (\n" +
                "    SELECT \n" +
                "        p.id AS product_id,\n" +
                "        SUM(od.quantity) AS buyQuantity,\n" +
                "        AVG(od.rating) AS avgRating\n" +
                "    FROM\n" +
                "        order_detail od\n" +
                "    LEFT JOIN orders o ON o.id = od.order_id\n" +
                "    LEFT JOIN product_detail pd ON pd.id = od.product_detail_id\n" +
                "    LEFT JOIN products p ON p.id = pd.product_id\n" +
                "    WHERE\n" +
                "        o.status = 3\n" +
                "    GROUP BY od.product_detail_id\n" +
                ") od ON od.product_id = p.id\n" +
                "LEFT JOIN (\n" +
                "    SELECT \n" +
                "        s.product_id AS productId,\n" +
                "        s.max_purchase\n" +
                "    FROM\n" +
                "        sales s\n" +
                "    WHERE\n" +
                "        (NOW() BETWEEN s.start_time AND s.end_time)\n" +
                "        AND s.`type` = 0\n" +
                ") tblSale ON tblSale.productId = p.id\n" +
                "LEFT JOIN (\n" +
                "    SELECT \n" +
                "        pi2.product_id,\n" +
                "        pi2.file_name\n" +
                "    FROM\n" +
                "        product_img pi2\n" +
                "    WHERE\n" +
                "        pi2.avatar = TRUE\n" +
                "        AND pi2.type = 'IMGPRODUCT'\n" +
                ") productIMG ON productIMG.product_id = p.id\n" +
                "WHERE\n" +
                "    1 = 1 \n");


        sql.append("SELECT \n" +
                "    p.id,\n" +
                "    p.product_code AS productCode,\n" +
                "    p.product_name AS productName,\n" +
                "    p.price,\n" +
                "    productIMG.file_name AS fileName,\n" +
                "    (p.price * (100 - tblSale.max_purchase) / 100) AS priceSale,\n" +
                "    tblSale.max_purchase AS purchase,\n" +
                "    od.buyQuantity,\n" +
                "    od.avgRating,\n" +
                "    c.categori_name AS categoriesName,\n" +
                "    brand.brand_name AS brandName\n" +
                "FROM\n" +
                "    products p\n" +
                "JOIN \n" +
                "    product_detail pd ON pd.product_id = p.id\n" +
                "LEFT JOIN \n" +
                "    product_img pi2 ON pi2.product_id = p.id\n" +
                "LEFT JOIN (\n" +
                "    SELECT \n" +
                "        p.id AS product_id,\n" +
                "        SUM(od.quantity) AS buyQuantity,\n" +
                "        AVG(od.rating) AS avgRating\n" +
                "    FROM\n" +
                "        order_detail od\n" +
                "    LEFT JOIN \n" +
                "        orders o ON o.id = od.order_id\n" +
                "    LEFT JOIN \n" +
                "        product_detail pd ON pd.id = od.product_detail_id\n" +
                "    LEFT JOIN \n" +
                "        products p ON p.id = pd.product_id\n" +
                "    WHERE\n" +
                "        o.status = 3\n" +
                "    GROUP BY \n" +
                "        od.product_detail_id\n" +
                ") od ON od.product_id = p.id\n" +
                "LEFT JOIN (\n" +
                "    SELECT \n" +
                "        s.product_id AS productId,\n" +
                "        s.max_purchase\n" +
                "    FROM\n" +
                "        sales s\n" +
                "    WHERE\n" +
                "        (NOW() BETWEEN s.start_time AND s.end_time)\n" +
                "        AND s.type = 0\n" +
                ") tblSale ON tblSale.productId = p.id\n" +
                "LEFT JOIN (\n" +
                "    SELECT \n" +
                "        pi2.product_id,\n" +
                "        pi2.file_name\n" +
                "    FROM\n" +
                "        product_img pi2\n" +
                "    WHERE\n" +
                "        pi2.avatar = TRUE\n" +
                "        AND pi2.type = 'IMGPRODUCT'\n" +
                ") productIMG ON productIMG.product_id = p.id\n" +
                "LEFT JOIN \n" +
                "    categories c ON p.categories_id = c.id\n" +
                "LEFT JOIN (\n" +
                "    SELECT \n" +
                "        bp.product_id, \n" +
                "        b.id, \n" +
                "        b.brand_name \n" +
                "    FROM \n" +
                "        brand b \n" +
                "    LEFT JOIN \n" +
                "        brand_product bp ON bp.brand_id = b.id\n" +
                "    GROUP BY \n" +
                "        bp.product_id\n" +
                ") brand ON brand.product_id = p.id \n" +
                "WHERE\n" +
                "    1 = 1 \n");
        if(StringUtils.isNotBlank(productRequestDTO.getNameSearch())){
            sql.append("AND ( :keySearch is null or p.product_name like concat('%',:keySearch,'%') ) \n");
            sqlCount.append("AND ( :keySearch is null or p.product_name like concat('%',:keySearch,'%') ) \n");
        }
        if(productRequestDTO.getStar()!=null){
            if(productRequestDTO.getStar().size()>0){
                sql.append(" AND ( ");
                sqlCount.append(" AND ( ");
                StringJoiner joiner = new StringJoiner(" OR ");
                for(int i =0 ;i<productRequestDTO.getStar().size();i++){
                    int star = productRequestDTO.getStar().get(i);
                    if(star == 5){
                        joiner.add(" od.avgRating = 5 ");
                    }else{
                        joiner.add(" ("+ star+" <= od.avgRating and od.avgRating < "+(star+1)+" ) ");
                    }
                }
                sqlCount.append(joiner.toString());
                sqlCount.append(" )");

                sql.append(joiner.toString());
                sql.append(" )");
            }
        }
        if(productRequestDTO.getCategoriesId()!=null){
            sql.append(" AND (p.categories_id =:categoriesId) ");
            sqlCount.append(" AND (p.categories_id =:categoriesId) ");
        }

        if(productRequestDTO.getFromPrice()!=null){
            sql.append("AND (\n" +
                    "\t\t:fromPrice IS NULL\n" +
                    "\t\tOR :fromPrice <= p.price\n" +
                    "\t) ");
            sqlCount.append("AND (\n" +
                    "\t\t:fromPrice IS NULL\n" +
                    "\t\tOR :fromPrice <= p.price\n" +
                    "\t) ");
        }
        if(productRequestDTO.getToPrice()!=null){
            sql.append("AND (\n" +
                    "\t\t:toPrice IS NULL\n" +
                    "\t\tOR :toPrice >= p.price\n" +
                    "\t)");
            sqlCount.append("AND (\n" +
                    "\t\t:toPrice IS NULL\n" +
                    "\t\tOR :toPrice >= p.price\n" +
                    "\t)");
        }

        sql.append("GROUP BY \n" +
                "    p.product_code,\n" +
                "    p.product_name,\n" +
                "    p.price,\n" +
                "    priceSale");

        if(StringUtils.isNotEmpty(productRequestDTO.getPropertySort())){
            String sort = productRequestDTO.getPropertySort();
            sql.append(" ORDER BY ");
            if(sort.equals("productName")){
                sql.append(sort+" ASC ");
            }else if (sort.equals("avgRating")){
                sql.append("avgRating DESC");
            } else if (sort.equals("price")) {
                sql.append("price DESC");
            }else if (sort.equals("buyQuantity")){
                sql.append("buyQuantity DESC");
            }
        }

        sqlCount.append("GROUP BY \n" +
                "    p.id ");

        Query query = entityManager.createNativeQuery(sql.toString());
        Query queryCount = entityManager.createNativeQuery("SELECT count(1) from ( " + sqlCount.toString()+" ) a");

        if(StringUtils.isNotBlank(productRequestDTO.getNameSearch())){
            query.setParameter("keySearch",productRequestDTO.getNameSearch());
            queryCount.setParameter("keySearch",productRequestDTO.getNameSearch());
        }
        if(productRequestDTO.getFromPrice()!=null){
            query.setParameter("fromPrice", productRequestDTO.getFromPrice());
            queryCount.setParameter("fromPrice", productRequestDTO.getFromPrice());
        }
        if(productRequestDTO.getToPrice()!=null){
            query.setParameter("toPrice", productRequestDTO.getToPrice());
            queryCount.setParameter("toPrice", productRequestDTO.getToPrice());
        }
        if (productRequestDTO.getCategoriesId()!=null){
            query.setParameter("categoriesId",productRequestDTO.getCategoriesId());
            queryCount.setParameter("categoriesId",productRequestDTO.getCategoriesId());
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<ProductSearchResponse> lstReturn = new ArrayList<>();

        List<Object[]> lstObj = query.getResultList();

        for(Object[] obj :lstObj){
            ProductSearchResponse productSearchResponse = new ProductSearchResponse();
            productSearchResponse.setId(DataUtil.safeToLong(obj[0]));
            productSearchResponse.setProductCode(DataUtil.safeToString(obj[1]));
            productSearchResponse.setProductName(DataUtil.safeToString(obj[2]));
            productSearchResponse.setPrice(DataUtil.safeToDouble(obj[3]));
            productSearchResponse.setFileName(DataUtil.safeToString(obj[4]));
            productSearchResponse.setPriceSale(DataUtil.safeToDouble(obj[5]));
            productSearchResponse.setPurchase(DataUtil.safeToDouble(obj[6]));
            productSearchResponse.setBuyQuantity(DataUtil.safeToLong(obj[7]));
            productSearchResponse.setAvgRating(DataUtil.safeToDouble(obj[8]));
            productSearchResponse.setCategoriesName(DataUtil.safeToString(obj[9]));
            productSearchResponse.setBrandName(DataUtil.safeToString(obj[10]));
            lstReturn.add(productSearchResponse);
        }
        long countResult = DataUtil.safeToLong( queryCount.getSingleResult());
        Page<ProductSearchResponse> pageReturn = new PageImpl<>(lstReturn,pageable,countResult);
        return pageReturn;
    }
}
