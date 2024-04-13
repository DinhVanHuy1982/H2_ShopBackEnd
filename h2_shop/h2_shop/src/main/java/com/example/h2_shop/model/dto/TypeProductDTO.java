package com.example.h2_shop.model.dto;

import jakarta.persistence.Column;

public class TypeProductDTO {
    private Long id;
    private String typeName; // tên của loại sản phẩm
    private String description; // mô tả của loại sản phẩm

    private Integer position; // vị tris trong danh sách type

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
