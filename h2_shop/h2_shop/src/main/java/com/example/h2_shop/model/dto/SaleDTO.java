package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.Product;
import jakarta.persistence.*;

import java.time.Instant;

public class SaleDTO {

    private long id;
    private String quantity;
    private String description;
    private Instant startTime;
    private Instant endTime;
    private Instant applyDate;

    private String type;
    private long minPrice;
    private long maxPrice;
    private long maxDiscount;
    private float maxPurchase;
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public SaleDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Instant getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Instant applyDate) {
        this.applyDate = applyDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }

    public long getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public long getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(long maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public float getMaxPurchase() {
        return maxPurchase;
    }

    public void setMaxPurchase(float maxPurchase) {
        this.maxPurchase = maxPurchase;
    }
}
