package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.Orders;
import com.example.h2_shop.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ProductImgDTO {

    private Long id;

    private String fileId;

    private String fileName;

    private Long order;

    private String fileSize;

    private Product product;

    private boolean avatar;
    private Orders orders;

    public ProductImgDTO() {

    }

    public ProductImgDTO(Long id, String fileId, String fileName, Long order, String fileSize, Product product, boolean avatar, Orders orders) {
        this.id = id;
        this.fileId = fileId;
        this.fileName = fileName;
        this.order = order;
        this.fileSize = fileSize;
        this.product = product;
        this.avatar = avatar;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isAvatar() {
        return avatar;
    }

    public void setAvatar(boolean avatar) {
        this.avatar = avatar;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
