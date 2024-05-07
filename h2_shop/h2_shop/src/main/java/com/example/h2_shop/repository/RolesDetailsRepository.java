package com.example.h2_shop.repository;

import com.example.h2_shop.model.RolesDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RolesDetailsRepository extends JpaRepository<RolesDetails,Long> {

//    public List<RolesDetails> saveAll(List<RolesDetails> list);
    @Query(value = "select\n" +
            "\trd.*\n" +
            "from\n" +
            "\tfunctions f\n" +
            "join role_details rd on\n" +
            "\trd.function_id = f.id\n" +
            "where\n" +
            "\trd.role_id = ?1", nativeQuery = true)
    List<RolesDetails> getListByRoleId(Long roleId);

    @Modifying
    @Query(value = "delete from role_details where role_id = :roleId",nativeQuery = true)
    int deleteRoleDetailByRoleId(@Param("roleId") Long roleId);

}
