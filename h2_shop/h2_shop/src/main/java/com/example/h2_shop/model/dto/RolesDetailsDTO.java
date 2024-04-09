package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.Function;
import com.example.h2_shop.model.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class RolesDetailsDTO {
    private Long id;

    private Roles role;

    private Long roleId;

    private Function function;

    private String action;

    private Long functionId;

    public RolesDetailsDTO() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public RolesDetailsDTO(Long id, Roles role, Function function, String action) {
        this.id = id;
        this.role = role;
        this.function = function;
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
