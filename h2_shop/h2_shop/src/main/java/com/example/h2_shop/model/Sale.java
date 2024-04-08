package com.example.h2_shop.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "quantity")
    private String quantity;
    @Column(name = "description")
    private String description;
    @Column(name = "start_time")
    private Instant startTime;
    @Column(name = "end_time")
    private Instant endTime;
    @Column(name = "apply_date")
    private Instant applyDate;
    @Column(name = "type")

    private String type;
    @Column(name = "minPrice")
    private long minPrice;
    @Column(name = "maxPrice")
    private long maxPrice;
    @Column(name = "max_discount")
    private long maxDiscount;
    @Column(name = "max_purchase")
    private float maxPurchase;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public Sale() {
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
