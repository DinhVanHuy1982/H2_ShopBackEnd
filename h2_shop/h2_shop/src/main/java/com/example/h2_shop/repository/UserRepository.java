package com.example.h2_shop.repository;

import com.example.h2_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select * from user",nativeQuery = true)
    List<User> findAllUsers();
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
    @Query(value = "select * from user u where u.role_id = :roleId",nativeQuery = true)
    Optional<User> findUserByRoles(@Param("roleId") Long roleId);
}
