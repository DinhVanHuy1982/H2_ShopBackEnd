package com.example.h2_shop.model.dto;

public class TypeSizeDTO {
    private String typeName;
    private String sizeName;
    private Long sizeId;
    private Long typeId;
    private Long quantity;

    private Long positionSize;
    private Long positionType;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPositionSize() {
        return positionSize;
    }

    public void setPositionSize(Long positionSize) {
        this.positionSize = positionSize;
    }

    public Long getPositionType() {
        return positionType;
    }

    public void setPositionType(Long positionType) {
        this.positionType = positionType;
    }
}
