package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.ProductDetail;
import com.example.h2_shop.model.User;

import java.time.Instant;

public class CartDTO {
    private Long id;

    private Long quantity;

    private ProductDetail productDetail;

    private User user;

    private Instant createTime;

    private Instant updateTime;

    private Long userId;

    private Long typeProductId;
    private Long sizeProductId;
    private Long productId;

    public CartDTO(Long id, Long quantity, ProductDetail productDetail, User user, Instant createTime, Instant updateTime) {
        this.id = id;
        this.quantity = quantity;
        this.productDetail = productDetail;
        this.user = user;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTypeProductId() {
        return typeProductId;
    }

    public void setTypeProductId(Long typeProductId) {
        this.typeProductId = typeProductId;
    }

    public Long getSizeProductId() {
        return sizeProductId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setSizeProductId(Long sizeProductId) {
        this.sizeProductId = sizeProductId;
    }
}
