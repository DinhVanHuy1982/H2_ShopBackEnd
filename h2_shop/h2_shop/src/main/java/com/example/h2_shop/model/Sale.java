package com.example.h2_shop.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;
    @Column(name = "quantity")
    private Long quantity;
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
    private Long minPrice;
    @Column(name = "maxPrice")
    private Long maxPrice;
    @Column(name = "max_discount")
    private Long maxDiscount;
    @Column(name = "max_purchase")
    private Float maxPurchase;
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

    public Sale(Long id, String code, String name, Long quantity, String description, Instant startTime, Instant endTime, Instant applyDate, String type, Long minPrice, Long maxPrice, Long maxDiscount, Float maxPurchase, Product product) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.applyDate = applyDate;
        this.type = type;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.maxDiscount = maxDiscount;
        this.maxPurchase = maxPurchase;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public Long getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Long getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Long maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public Float getMaxPurchase() {
        return maxPurchase;
    }

    public void setMaxPurchase(Float maxPurchase) {
        this.maxPurchase = maxPurchase;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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
}
