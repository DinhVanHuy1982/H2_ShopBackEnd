package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.Orders;
import com.example.h2_shop.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ProductImgDTO {

    private Long id;

    private Long fileId;

    private String fileName;

    private String type;

    private String fileSize;

    private Product product;

    private boolean avatar;
    private Orders orders;

    public ProductImgDTO() {

    }

    public ProductImgDTO(Long id, Long fileId, String fileName, String type, String fileSize, Product product, boolean avatar, Orders orders) {
        this.id = id;
        this.fileId = fileId;
        this.fileName = fileName;
        this.type = type;
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

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
