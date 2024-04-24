package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.RolesDTO;
import com.example.h2_shop.model.dto.RolesSearchDTO;
import com.example.h2_shop.service.RolesService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final  RolesService rolesService;

    public RolesController(RolesService rolesService){
        this.rolesService=rolesService;
    }

    @GetMapping("/getAllRole")
    public ResponseEntity<ServiceResult<List<RolesDTO>>> getAllRoleWithNoPage(){
        List<RolesDTO> rolesDTOList = this.rolesService.getAllRole();
        return ResponseEntity.ok(new ServiceResult<>(rolesDTOList,HttpStatus.OK,"Lấy dữ liệu thành công" ));
    }

    @PostMapping("/getAllRoleWithPage")
    public ServiceResult<Page<RolesDTO>> getAllRoleWithPage(@RequestBody RolesSearchDTO rolesSearchDTO){
        return new ServiceResult<>(this.rolesService.getRoleWithPage(rolesSearchDTO),HttpStatus.OK,"ok");
    }

    @PostMapping("/createRole")
    public ServiceResult<?> createNewRole(@RequestBody RolesDTO rolesDTO){
        ServiceResult<?> serviceResult = this.rolesService.createRole(rolesDTO);
        return serviceResult;
    }
}
