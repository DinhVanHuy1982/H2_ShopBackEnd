package com.example.h2_shop.repository;

import com.example.h2_shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select * from user",nativeQuery = true)
    List<User> findAllUsers();

    @Query(value = "SELECT \n" +
            "    u.id,\n" +
            "    u.full_name AS fullName,\n" +
            "    u.user_name AS userName,\n" +
            "    u.phone_number AS phoneNumber,\n" +
            "    u.status,\n" +
            "    u.create_time AS createTime,\n" +
            "    u.email,\n" +
            "    r.role_name AS roleName\n" +
            "FROM \n" +
            "    `user` u\n" +
            "LEFT JOIN \n" +
            "    roles r ON r.id = u.role_id \n" +
            "WHERE \n" +
            "    (:keySearch IS NULL OR u.user_name LIKE CONCAT('%', :keySearch, '%') OR u.full_name LIKE CONCAT('%', :keySearch, '%'))\n" +
            "    AND (:status IS NULL OR :status = u.status);\n", nativeQuery = true)
    Page<Map<String,Object>> searchUser(Pageable pageable,@Param("keySearch") String keySearch, @Param("status") Long status);

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
    @Query(value = "select * from user u where u.role_id = :roleId",nativeQuery = true)
    Optional<User> findUserByRoles(@Param("roleId") Long roleId);

    @Query(value = "select\n" +
            "\tu.id,\n" +
            "\tu.full_name fullName,\n" +
            "\tu.avatar ,\n" +
            "\tu.user_name username,\n" +
            "\tu.email ,\n" +
            "\tu.phone_number phoneNumber,\n" +
            "\tu.province_id provinceId,\n" +
            "\tu.district_id districtId,\n" +
            "\tu.ward,\n" +
            "\tu.status,\n" +
            "\tu.description ,\n" +
            "\tu.role_id roleId\n" +
            "from\n" +
            "\t`user` u\n" +
            "where\n" +
            "\tu.id =:id", nativeQuery = true)
    Map<String,Object> detailInforUser(@Param("id")Long id);
}
