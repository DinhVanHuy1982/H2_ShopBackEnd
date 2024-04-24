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

    @Query(value = "select r.id, r.create_name createName, r.create_time createTime, r.description description,r.role_code roleCode,\n" +
            " r.role_name roleName, r.update_name updateName, r.update_time updateTime  , count(u.role_id) as UserUse , rd.function_id, rd.role_id, rd.action\n" +
            "from roles r \n" +
            " join role_details rd on rd.role_id = r.id  \n" +
            " left join `user` u on u.role_id = r.id \n" +
            " group by r.id, rd.function_id", nativeQuery = true)
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
