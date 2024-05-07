package com.example.h2_shop.repository.customRepo;

import com.example.h2_shop.commons.DataUtil;
import com.example.h2_shop.model.Categories;
import com.example.h2_shop.model.dto.CategoriesDTO;
import com.example.h2_shop.model.mapper.CategoriesMapper;
import com.example.h2_shop.repository.CategoriesRepositoryCustome;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CategoriesRepositoryCustomeCustomeImpl implements CategoriesRepositoryCustome {

    private final CategoriesMapper categoriesMapper;

    @Autowired
    EntityManager entityManager;

    public CategoriesRepositoryCustomeCustomeImpl(CategoriesMapper categoriesMapper){
        this.categoriesMapper=categoriesMapper;
//        this.entityManager=entityManager;
    }
    @Override
    public List<CategoriesDTO> getAllCategoriesActive(CategoriesDTO categoriesDTOSearch) {
        String sql = "WITH RECURSIVE cte (id, parent_id) AS (\n" +
                "    SELECT ct.id, ct.parent_id\n" +
                "    FROM categories ct" +
                "    where 1=1 and (:status is null or ct.status=:status) and (upper(ct.categori_name) like (:search) or upper(ct.categori_code) like (:search) or :search is null)\n" +
                "    UNION \n" +
                "    SELECT ct2.id, ct2.parent_id\n" +
                "    FROM categories ct2\n" +
                "    INNER JOIN cte ON ct2.id = cte.parent_id\n" +
                ") \n" +
                "SELECT ct.id, ct.categori_code, ct.categori_name, ct.parent_id,ct.create_time , ct.status , ct.description \n" +
                "FROM cte\n" +
                "LEFT JOIN categories ct ON cte.id = ct.id \n" +
                "ORDER BY ct.categori_code  ASC;";

        Query query = this.entityManager.createNativeQuery(sql);
        if(categoriesDTOSearch.getId()==null && StringUtils.isNotBlank(categoriesDTOSearch.getStatus())){
            query.setParameter("status",categoriesDTOSearch.getStatus());
        }else{
            query.setParameter("status",null);
        }
        if(StringUtils.isNotBlank(categoriesDTOSearch.getKeySearch())){
            query.setParameter("search", categoriesDTOSearch.getKeySearch());
        }else{
            query.setParameter("search", null);
        }

        List<Object[]> listObj = query.getResultList();

        List<CategoriesDTO> categoriesDTOList = new ArrayList<>();
        for(Object[] obj : listObj){
            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setId((Long) obj[0]);
            categoriesDTO.setCategoriCode((String) obj[1]);
            categoriesDTO.setCategoriName((String) obj[2]);
            categoriesDTO.setParentId((Long) obj[3]);
            categoriesDTO.setCreateTime(DataUtil.safeToInstant(obj[4]));
            categoriesDTO.setStatus((String) obj[5]);
            categoriesDTO.setDescription((String) obj[6]);
            categoriesDTOList.add(categoriesDTO);
        }

//        List<CategoriesDTO> categoriesDTOList = this.categoriesMapper.toDto(categoriesList);
        return categoriesDTOList;
    }
}
