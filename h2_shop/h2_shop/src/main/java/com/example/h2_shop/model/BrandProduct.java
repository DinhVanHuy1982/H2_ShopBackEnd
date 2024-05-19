package com.example.h2_shop.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "brand_product")
public class BrandProduct {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "import_quantity")
    private Long importQuantity;
    @Column(name = "import_price")
    private Long importPrice;
    @Column(name = "import_date")
    private Instant importDate;
    @Column(name = "category_code")
    private String categoryCode;

    @Column(name = "product_detail_id")
    private Long productDetailId;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name="brand_id")
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

    public BrandProduct() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImportQuantity() {
        return importQuantity;
    }

    public void setImportQuantity(Long importQuantity) {
        this.importQuantity = importQuantity;
    }

    public Long getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(Long importPrice) {
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

    public Long getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(Long productDetailId) {
        this.productDetailId = productDetailId;
    }
}
