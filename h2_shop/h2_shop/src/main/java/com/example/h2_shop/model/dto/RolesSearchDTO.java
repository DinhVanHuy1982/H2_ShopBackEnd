package com.example.h2_shop.model.dto;

public class RolesSearchDTO {
    private String roleSearchName;
    private Integer status;
    private Integer page;

    public String getRoleSearchName() {
        return roleSearchName;
    }

    public void setRoleSearchName(String roleSearchName) {
        this.roleSearchName = roleSearchName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
