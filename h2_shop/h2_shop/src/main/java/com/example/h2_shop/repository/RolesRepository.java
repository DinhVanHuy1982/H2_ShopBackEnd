package com.example.h2_shop.repository;

import com.example.h2_shop.model.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query(value = "select\n" +
            "\tr.id,\n" +
            "\tr.create_name createName,\n" +
            "\tr.create_time createTime,\n" +
            "\tr.description description,\n" +
            "\tr.role_code roleCode,\n" +
            "\tr.role_name roleName,\n" +
            "\tr.update_name updateName,\n" +
            "\tr.update_time updateTime ,\n" +
            "\tcount(u.role_id) as UserUse\n" +
            "from\n" +
            "\troles r\n" +
            "left join `user` u \n" +
            "on\n" +
            "\tu.role_id = r.id\n" +
            "group by\n" +
            "\tr.id,\n" +
            "\tr.create_name,\n" +
            "\tr.create_time,\n" +
            "\tr.description,\n" +
            "\tr.role_code,\n" +
            "\tr.role_name,\n" +
            "\tr.update_name,\n" +
            "\tr.update_time", nativeQuery = true)
    public List<Map<String,Object>> getAllRoles();

    public Optional<Roles> findByRoleCode(String roleCode);

    @Query(value = "select\n" +
            "\tdistinct\n" +
            "    r.id AS id,\n" +
            "    r.create_name AS createTime,\n" +
            "    r.create_time AS createTime,\n" +
            "    r.description AS description,\n" +
            "    r.role_code AS roleCode,\n" +
            "    r.role_name AS roleName,\n" +
            "    r.update_name AS updateName,\n" +
            "    r.update_time AS updateTime,\n" +
            "    r.status,\n" +
            "    COUNT(u.role_id) AS userUse\n" +
            "FROM\n" +
            "    roles r\n" +
            "left JOIN \n" +
            "    role_details rd ON rd.role_id = r.id\n" +
            "LEFT JOIN \n" +
            "    user u ON u.role_id = r.id\n" +
            "WHERE\n" +
            "    (?1 IS NULL OR r.role_name LIKE CONCAT('%', ?1, '%') OR r.role_code LIKE CONCAT('%', ?1, '%')) AND\n" +
            "     (?2 IS NULL OR ?2 = r.status)\n" +
            "GROUP BY \n" +
            "    r.id, rd.function_id",nativeQuery = true)
    Page<Map<String,Object>> findRoleWithPage(Pageable pageable, String searchName, Integer status);
}
