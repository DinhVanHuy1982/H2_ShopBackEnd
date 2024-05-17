package com.example.h2_shop.model.dto;

import java.util.List;

public class RoleDetailReturnDTO {
    private Long id;
    private String roleName;
    private String roleCode;
    private Integer status;
    private String description;
    private List<FunctionsDTO> lstFunction;

    public RoleDetailReturnDTO() {
    }

    public RoleDetailReturnDTO(Long id, String roleName, Integer status, String description, List<FunctionsDTO> lstFunction) {
        this.id = id;
        this.roleName = roleName;
        this.status = status;
        this.description = description;
        this.lstFunction = lstFunction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FunctionsDTO> getLstFunction() {
        return lstFunction;
    }

    public void setLstFunction(List<FunctionsDTO> lstFunction) {
        this.lstFunction = lstFunction;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
