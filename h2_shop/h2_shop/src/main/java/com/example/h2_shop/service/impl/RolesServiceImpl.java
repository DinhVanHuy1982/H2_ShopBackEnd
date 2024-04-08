package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.dto.RolesDTO;
import com.example.h2_shop.repository.customRepo.RoleRepositoryCustom;
import com.example.h2_shop.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RolesServiceImpl implements RolesService {

    @Autowired
    RoleRepositoryCustom roleRepositoryCustom;
    @Override
    public List<RolesDTO> getAllRole() {
        return roleRepositoryCustom.getSearchAllRoleWithNoPage();
    }
}
