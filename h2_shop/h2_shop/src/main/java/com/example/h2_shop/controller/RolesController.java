package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.FunctionsDTO;
import com.example.h2_shop.model.dto.RoleDetailReturnDTO;
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

    /**
     * lấy ra role và chi tiết các chức năng của nó
     *
     * @param id : id của role cần lấy thông tin
     * @return
     * @throws
     * @author admin
     * @since 4/25/2024
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0
     */
    @GetMapping("/get-detail-role-byId/{id}")
    public ServiceResult<RoleDetailReturnDTO> getDetailRoleById(@PathVariable Long id){
        return this.rolesService.getDetailRole(id);
    }

    @PostMapping("/update-role")
    public ServiceResult<?> updateRole(@RequestBody RolesDTO rolesDTO){
        return this.rolesService.updateRole(rolesDTO);
    }

    @GetMapping("/get-all")
    public ServiceResult<?> searchAllRole(){
        return new ServiceResult<>(this.rolesService.getAllRole(),HttpStatus.OK,"");
    }

    @GetMapping("delete-role/{id}")
    public ServiceResult<?> deleteRoleById(@PathVariable Long id){
        return this.rolesService.deleteRoleById(id);
    }
}
