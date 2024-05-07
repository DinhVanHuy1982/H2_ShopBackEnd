package com.example.h2_shop.repository;

import com.example.h2_shop.model.Size;
import com.example.h2_shop.model.TypeProduct;
import com.example.h2_shop.model.dto.TypeProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@Transactional
public interface TypeProductRepository extends JpaRepository<TypeProduct, Long> {
    @Query(value = "select distinct tp.* from type_product tp join product_detail pd on pd.size_id = tp.id where pd.product_id = :productId",nativeQuery = true)
    public List<TypeProduct> getTypeOfProduct(@Param("productId") Long productId);

    @Query(value="SELECT s.id AS sizeId, s.size_name AS sizeName, tp.id AS typeId, tp.type_name AS typeName, pd.quantity AS quantity\n" +
            "FROM product_detail pd\n" +
            "JOIN sizes s ON s.id = pd.size_id \n" +
            "JOIN type_product tp ON tp.id = pd.type_product_id \n" +
            "WHERE pd.product_id = :productId\n", nativeQuery = true)
    public List<Map<String, Object>> getSizeTypeDTO(@Param("productId")Long productId);
}
