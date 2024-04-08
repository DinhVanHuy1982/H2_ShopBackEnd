package com.example.h2_shop.model.dto;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

public class CategoriesDTO {
    private long id;
    private String categoriName;
    private String categoriCode;
    private long parentId;
    private Instant createTime;
    private String description;

    private List<CategoriesDTO> categoriesDTOList;

    public CategoriesDTO() {
    }

    public List<CategoriesDTO> getCategoriesDTOList() {
        return categoriesDTOList;
    }

    public void setCategoriesDTOList(List<CategoriesDTO> categoriesDTOList) {
        this.categoriesDTOList = categoriesDTOList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoriName() {
        return categoriName;
    }

    public void setCategoriName(String categoriName) {
        this.categoriName = categoriName;
    }

    public String getCategoriCode() {
        return categoriCode;
    }

    public void setCategoriCode(String categoriCode) {
        this.categoriCode = categoriCode;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
