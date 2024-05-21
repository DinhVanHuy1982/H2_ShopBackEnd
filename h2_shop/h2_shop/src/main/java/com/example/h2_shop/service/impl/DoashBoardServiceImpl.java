package com.example.h2_shop.service.impl;

import com.example.h2_shop.commons.DataUtil;
import com.example.h2_shop.model.dto.DiagramDetailDTO;
import com.example.h2_shop.service.DoashBoardService;
import com.example.h2_shop.service.ServiceResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.classic.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DoashBoardServiceImpl implements DoashBoardService {
    @Autowired
    EntityManager entityManager;


    @Override
    public ServiceResult<DiagramDetailDTO> getDataForClient(Integer year){
        DiagramDetailDTO diagramDetailDTO = new DiagramDetailDTO();

        // TODO: 5/15/2024 lấy ra doanh thu
        String sql = "select\n" +
                "\tif(sum(o.price),\n" +
                "\tsum(o.price),\n" +
                "\t0) doanh_thu\n" +
                "\tfrom\n" +
                "\t(\n" +
                "\tselect\n" +
                "\t\t1 as month\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t2\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t3\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t4\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t5\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t6\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t7\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t8\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t9\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t10\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t11\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t12\n" +
                "    ) as months\n" +
                "left join orders o on\n" +
                "\tmonths.month = month(o.order_date)\n" +
                "\tand (year(o.order_date) = :year \n" +
                "\t\t and o.pay_status=1)\n" +
                "group by\n" +
                "\tmonths.month";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("year",year);

        List<Object> objectList = query.getResultList();
        List<Double> doubleList = new ArrayList<>();
        for(Object object:objectList){
            doubleList.add(DataUtil.safeToDouble(object));
        }
        diagramDetailDTO.setDataTurnover(doubleList);

        String sql2="select\n" +
                "\tif(sum(bp.import_price),\n" +
                "\t sum(bp.import_price * bp.import_quantity),\n" +
                "\t0) as chi_tieu\n" +
                "\tfrom\n" +
                "\t(\n" +
                "\tselect\n" +
                "\t\t1 as month\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t2\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t3\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t4\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t5\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t6\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t7\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t8\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t9\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t10\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t11\n" +
                "union\n" +
                "\tselect\n" +
                "\t\t12\n" +
                "    ) as months\n" +
                "left join brand_product bp on\n" +
                "\tmonths.month = month(bp.import_date)\n" +
                "\tand (year(bp.import_date) = :year)\n" +
                "group by\n" +
                "\tmonths.month";
        Query query2 = entityManager.createNativeQuery(sql2);
        query2.setParameter("year",year);

        List<Object> objectList2 = query2.getResultList();
        List<Double> doubleList2 = new ArrayList<>();
        for(Object object:objectList2){
            doubleList2.add(DataUtil.safeToDouble(object));
        }
        diagramDetailDTO.setDataspend(doubleList2);




        return new ServiceResult<>(diagramDetailDTO, HttpStatus.OK,"");
    }

}
