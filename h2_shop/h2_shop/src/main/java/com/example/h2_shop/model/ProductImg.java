package com.example.h2_shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_img")
public class ProductImg {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="file_id")
    private String fileId;

    @Column(name="file_name")
    private String fileName;

    @Column(name="order_img")
    private Long order;

    @Column(name="file_size")
    private String fileSize;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;


    @Column(name="avatar")
    private boolean avatar;

    @ManyToOne
    @JoinColumn(name ="order_id")
    private Orders orders;



    public ProductImg() {
    }

    public ProductImg(Long id, String fileId, String fileName, Long order, String fileSize, Product product, boolean avatar) {
        this.id = id;
        this.fileId = fileId;
        this.fileName = fileName;
        this.order = order;
        this.fileSize = fileSize;
        this.product = product;
        this.avatar = avatar;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
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
}
