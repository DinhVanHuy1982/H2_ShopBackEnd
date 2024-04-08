package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.Brands;
import com.example.h2_shop.model.Product;
import jakarta.persistence.*;

import java.time.Instant;


public class BrandProductDTO {
    private long id;
    private long importQuantity;
    private long importPrice;
    private Instant importDate;
    private String categoryCode;

    private Product product;
    private Brands brands;

    public Brands getBrands() {
        return brands;
    }

    public void setBrands(Brands brands) {
        this.brands = brands;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BrandProductDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getImportQuantity() {
        return importQuantity;
    }

    public void setImportQuantity(long importQuantity) {
        this.importQuantity = importQuantity;
    }

    public long getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(long importPrice) {
        this.importPrice = importPrice;
    }

    public Instant getImportDate() {
        return importDate;
    }

    public void setImportDate(Instant importDate) {
        this.importDate = importDate;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
}
