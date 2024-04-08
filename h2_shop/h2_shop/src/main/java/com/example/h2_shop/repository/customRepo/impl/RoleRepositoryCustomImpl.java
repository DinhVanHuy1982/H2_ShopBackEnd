package com.example.h2_shop.repository.customRepo.impl;

import com.example.h2_shop.commons.ReflectorUtil;
import com.example.h2_shop.model.dto.RolesDTO;
import com.example.h2_shop.repository.RolesRepository;
import com.example.h2_shop.repository.customRepo.RoleRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.descriptor.java.InstantJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {

    private EntityManager entityManager;
    @Autowired
    RolesRepository rolesRepository;

    public RoleRepositoryCustomImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public List<RolesDTO> getSearchAllRoleWithNoPage() {
        String sql = "select r.id, r.create_name createName, r.create_time createTime," +
                " r.description description,r.role_code roleCode, r.role_name roleName, r.update_name updateName, r.update_time updateTime  , count(u.role_id) as UserUse \n" +
                "from roles r left join `user` u \n" +
                "on u.role_id = r.id "+
                " GROUP BY r.id, r.create_name, r.create_time, r.description, r.role_code, r.role_name, r.update_name, r.update_time";

//        NativeQuery<RolesDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

//        Query query1 = this.entityManager.createQuery(sql);

        List<Map<String,Object>> RoleDTO =this.rolesRepository.getAllRoles();



        List<RolesDTO> rolesDTOS = RoleDTO.stream().map(obj -> ReflectorUtil.mapToDTO(obj,RolesDTO.class)).collect(Collectors.toList());

        return rolesDTOS;
    }
}
