package com.example.h2_shop.repository;

import com.example.h2_shop.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query(value = "select r.id, r.create_name createName, r.create_time createTime, r.description description,r.role_code roleCode,\n" +
            " r.role_name roleName, r.update_name updateName, r.update_time updateTime  , count(u.role_id) as UserUse , rd.function_id, rd.role_id, rd.action\n" +
            "from roles r \n" +
            " join role_details rd on rd.role_id = r.id  \n" +
            " left join `user` u on u.role_id = r.id \n" +
            " group by r.id, rd.function_id", nativeQuery = true)
    public List<Map<String,Object>> getAllRoles();
}
