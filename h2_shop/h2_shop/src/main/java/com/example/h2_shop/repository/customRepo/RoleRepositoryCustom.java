package com.example.h2_shop.repository.customRepo;

import com.example.h2_shop.model.dto.RolesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepositoryCustom {

    public List<RolesDTO> getSearchAllRoleWithNoPage();

}
