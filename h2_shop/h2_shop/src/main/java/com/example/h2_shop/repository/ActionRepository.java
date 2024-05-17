package com.example.h2_shop.repository;

import com.example.h2_shop.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action,Long> {

    @Query(value = "select * from actions a where FIND_IN_SET(a.id, REPLACE(:lstAction, \" \", \"\")) > 0;",nativeQuery = true)
    public List<Action> getActionByListId(@Param("lstAction") String concatId);

}
