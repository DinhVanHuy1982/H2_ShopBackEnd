package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.Categories;
import jakarta.persistence.*;

import java.time.Instant;

public class ProductDTO {
    private long id;
    private String productCode;
    private String productName;
    private long price;
    private long quantity;
    private String description;
    private Instant createTime;
    private Instant updateTime;
    private String typeWarranty;
    private long warranty;
    private Categories categories;

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public ProductDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
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

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getTypeWarranty() {
        return typeWarranty;
    }

    public void setTypeWarranty(String typeWarranty) {
        this.typeWarranty = typeWarranty;
    }

    public long getWarranty() {
        return warranty;
    }

    public void setWarranty(long warranty) {
        this.warranty = warranty;
    }
}
