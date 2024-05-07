package com.example.h2_shop.repository;

import com.example.h2_shop.model.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionRepository extends JpaRepository<Function, Long> {

    @Query(value = "select\n" +
            "\tf.*\n" +
            "from\n" +
            "\tfunctions f\n" +
            "join role_details rd on\n" +
            "\trd.function_id = f.id\n" +
            "where\n" +
            "\trd.role_id = :roleId", nativeQuery = true)
    List<Function> getByRoleId(Long roleId);
}
