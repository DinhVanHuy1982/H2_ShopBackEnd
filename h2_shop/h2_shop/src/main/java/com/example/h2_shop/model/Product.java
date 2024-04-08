package com.example.h2_shop.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "product_code")
    private String productCode;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "price")
    private long price;
    @Column(name = "quantity")
    private long quantity;
    @Column(name = "description")
    private String description;
    @Column(name = "create_time")
    private Instant createTime;
    @Column(name = "update_time")
    private Instant updateTime;
    @Column(name = "type_warranty")
    private String typeWarranty;
    @Column(name = "warranty")
    private long warranty;
    @ManyToOne
    @JoinColumn(name="categories_id")
    private Categories categories;

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public Product() {
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
