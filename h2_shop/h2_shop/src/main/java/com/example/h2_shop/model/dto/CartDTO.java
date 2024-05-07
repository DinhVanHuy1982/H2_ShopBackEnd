package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.ProductDetail;
import com.example.h2_shop.model.User;
import lombok.Getter;

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
    private String typeProductName;
    private String sizeProductName;
    private Long residualQuantity;
    private Long sizeProductId;
    private Long productId;
    private boolean isSlected;
    private String productName;
    private String fileName;
    public CartDTO() {
    }

    public CartDTO(Long id, Long quantity, ProductDetail productDetail, User user, Instant createTime, Instant updateTime) {
        this.id = id;
        this.quantity = quantity;
        this.productDetail = productDetail;
        this.user = user;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public CartDTO(String fileName,Long id,String productName, Long quantity, Long typeProductId, String typeProductName, String sizeProductName, Long residualQuantity, Long sizeProductId, Long productId, boolean isSlected) {
        this.id = id;
        this.quantity = quantity;
        this.typeProductId = typeProductId;
        this.typeProductName = typeProductName;
        this.sizeProductName = sizeProductName;
        this.residualQuantity = residualQuantity;
        this.sizeProductId = sizeProductId;
        this.productId = productId;
        this.isSlected = isSlected;
        this.productName=productName;
        this.fileName=fileName;
    }

    public CartDTO(Long id, Long quantity, ProductDetail productDetail, User user, Instant createTime, Instant updateTime, Long userId, Long typeProductId, String typeProductName, String sizeProductName, Long residualQuantity, Long sizeProductId, Long productId, boolean isSlected) {
        this.id = id;
        this.quantity = quantity;
        this.productDetail = productDetail;
        this.user = user;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.userId = userId;
        this.typeProductId = typeProductId;
        this.typeProductName = typeProductName;
        this.sizeProductName = sizeProductName;
        this.residualQuantity = residualQuantity;
        this.sizeProductId = sizeProductId;
        this.productId = productId;
        this.isSlected = isSlected;
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

    public String getTypeProductName() {
        return typeProductName;
    }

    public void setTypeProductName(String typeProductName) {
        this.typeProductName = typeProductName;
    }

    public String getSizeProductName() {
        return sizeProductName;
    }

    public void setSizeProductName(String sizeProductName) {
        this.sizeProductName = sizeProductName;
    }

    public Long getResidualQuantity() {
        return residualQuantity;
    }

    public void setResidualQuantity(Long residualQuantity) {
        this.residualQuantity = residualQuantity;
    }

    public boolean isSlected() {
        return isSlected;
    }

    public void setSlected(boolean slected) {
        isSlected = slected;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProductName() {
        return productName;
    }

    public String getFileName() {
        return fileName;
    }
}
