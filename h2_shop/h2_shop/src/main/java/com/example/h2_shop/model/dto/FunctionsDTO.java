package com.example.h2_shop.model.dto;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class FunctionsDTO implements Serializable {

    private Long id;
    private String functionName;
    private String functionCode;
    private Instant createTime;
    private String createName;
    private String description;

    private List<Long> listIdAction;
    private Boolean checkApplyFunction;
    private Long parentId;

    private List<ActionsDTO> listActionDTO;

    public FunctionsDTO(){}

    public List<ActionsDTO> getListActionDTO() {
        return listActionDTO;
    }

    public void setListActionDTO(List<ActionsDTO> listActionDTO) {
        this.listActionDTO = listActionDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getCheckApplyFunction() {
        return checkApplyFunction;
    }

    public void setCheckApplyFunction(Boolean checkApplyFunction) {
        this.checkApplyFunction = checkApplyFunction;
    }

    public List<Long> getListIdAction() {
        return listIdAction;
    }

    public void setListIdAction(List<Long> listIdAction) {
        this.listIdAction = listIdAction;
    }
}
