package com.example.h2_shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_img")
public class ProductImg {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="file_id")
    private String fileId;

    @Column(name="file_name")
    private String fileName;

    @Column(name="order_img")
    private long order;

    @Column(name="file_size")
    private String fileSize;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;


    @Column(name="avatar")
    private boolean avatar;

    public ProductImg() {
    }

    public ProductImg(long id, String fileId, String fileName, long order, String fileSize, Product product, boolean avatar) {
        this.id = id;
        this.fileId = fileId;
        this.fileName = fileName;
        this.order = order;
        this.fileSize = fileSize;
        this.product = product;
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
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
}
