package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.RolesDTO;
import com.example.h2_shop.model.dto.RolesSearchDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RolesService {
    public List<RolesDTO> getAllRole();
    Page<RolesDTO> getRoleWithPage(RolesSearchDTO rolesSearchDTO);

    public ServiceResult<?> createRole(RolesDTO rolesDTO);
}
