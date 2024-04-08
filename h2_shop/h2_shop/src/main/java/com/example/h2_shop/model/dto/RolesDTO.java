package com.example.h2_shop.model.dto;


import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class RolesDTO{

    private Long id;

    private String code;

    private String name;

    private Integer status;

    private String description;

    private Instant createTime;

    private String createName;

    private Instant updateTime;

    private String updateName;

    private Long countUser;
    private String functionId;
    private String functionCode;
    private List<String> functionAction;
    private List<FunctionsDTO> listFunctions;
    private String action;
    private String textSearch;
    private String order;
    private String orderName;
    private Boolean allRole = false;

    private long userUse;

    public long getUserUse() {
        return userUse;
    }

    public void setUserUse(long userUse) {
        this.userUse = userUse;
    }

    public Boolean getAllRole() {
        return allRole;
    }

    public void setAllRole(Boolean allRole) {
        this.allRole = allRole;
    }

    public Long getCountUser() {
        return countUser;
    }

    public void setCountUser(Long countUser) {
        this.countUser = countUser;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public List<String> getFunctionAction() {
        return functionAction;
    }

    public void setFunctionAction(List<String> functionAction) {
        this.functionAction = functionAction;
    }

    public List<FunctionsDTO> getListFunctions() {
        return listFunctions;
    }

    public void setListFunctions(List<FunctionsDTO> listFunctions) {
        this.listFunctions = listFunctions;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RolesDTO)) {
            return false;
        }

        RolesDTO rolesDTO = (RolesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rolesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RolesDTO{" +
                "id=" + getId() +
                ", code='" + getCode() + "'" +
                ", name='" + getName() + "'" +
                ", status=" + getStatus() +
                ", description='" + getDescription() + "'" +
                ", createTime='" + getCreateTime() + "'" +
                ", createName='" + getCreateName() + "'" +
                ", updateTime='" + getUpdateTime() + "'" +
                ", updateName='" + getUpdateName() + "'" +
                "}";
    }
}
