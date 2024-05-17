package com.example.h2_shop.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "product_img")
public class ProductImg {
    @Getter
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="file_id")
    private String fileId;

    @Column(name="file_name")
    private String fileName;

    @Column(name="type")
    private String type;

    @Column(name="file_size")
    private String fileSize;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;


    @Column(name="avatar")
    private boolean avatar;

    @ManyToOne
    @JoinColumn(name ="order_detail_id")
    private OrderDetail orderDetail;



    public ProductImg() {
    }

    public ProductImg(Long id, String fileId, String fileName, String type, String fileSize, Product product, boolean avatar, OrderDetail orderDetail) {
        this.id = id;
        this.fileId = fileId;
        this.fileName = fileName;
        this.type = type;
        this.fileSize = fileSize;
        this.product = product;
        this.avatar = avatar;
        this.orderDetail = orderDetail;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
}
