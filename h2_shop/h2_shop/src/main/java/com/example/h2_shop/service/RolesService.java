package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.RolesDTO;

import java.util.List;

public interface RolesService {
    public List<RolesDTO> getAllRole();

    public ServiceResult<?> createRole(RolesDTO rolesDTO);
}
