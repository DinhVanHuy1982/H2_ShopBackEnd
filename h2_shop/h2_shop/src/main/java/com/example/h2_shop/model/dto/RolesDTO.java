package com.example.h2_shop.model.dto;

import java.time.Instant;
import java.util.List;

public class RolesDTO{

    private Long id;

    private Instant createTime;
    private Instant updateTime;
    private String createName;
    private String updateName;
    private  String description;

    private String roleName;
    private String roleCode;

    private List<FunctionsDTO> listFunction;

    private List<RolesDetailsDTO> listRolesDetailsDTO;

    public RolesDTO() {
    }

    public RolesDTO(Long id, String roleName, String roleCode, Instant createTime, Instant updateTime, String createName, String updateName, String description, List<FunctionsDTO> listFunction, List<RolesDetailsDTO> listRolesDetailsDTO) {
        this.id = id;
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createName = createName;
        this.updateName = updateName;
        this.description = description;
        this.listFunction = listFunction;
        this.listRolesDetailsDTO = listRolesDetailsDTO;
    }


    public List<FunctionsDTO> getListFunction() {
        return listFunction;
    }

    public List<RolesDetailsDTO> getListRolesDetailsDTO() {
        return listRolesDetailsDTO;
    }

    public void setListRolesDetailsDTO(List<RolesDetailsDTO> listRolesDetailsDTO) {
        this.listRolesDetailsDTO = listRolesDetailsDTO;
    }

    public void setListFunction(List<FunctionsDTO> listFunction) {
        this.listFunction = listFunction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
