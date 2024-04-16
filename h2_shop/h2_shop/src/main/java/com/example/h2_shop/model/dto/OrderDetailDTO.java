package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.Orders;
import com.example.h2_shop.model.Product;
import com.example.h2_shop.model.ProductDetail;
import jakarta.persistence.Column;

public class OrderDetailDTO {
    private Long id;

    private Long quantity;

    private Long orderId;
    private Orders orders;

    private Long productId;

    private ProductDetail productDetail;

    private Float price;

    private Long typeProductId;
    private Long sizeProductId;
    private String comment;
    private Integer rating;
    private String replyComment;

    public OrderDetailDTO(Long id, Long quantity, Long orderId, Orders orders, Long productId, ProductDetail productDetail, Float price, Long typeProductId, Long sizeProductId, String comment, Integer rating, String replyComment) {
        this.id = id;
        this.quantity = quantity;
        this.orderId = orderId;
        this.orders = orders;
        this.productId = productId;
        this.productDetail = productDetail;
        this.price = price;
        this.typeProductId = typeProductId;
        this.sizeProductId = sizeProductId;
        this.comment = comment;
        this.rating = rating;
        this.replyComment = replyComment;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public void setSizeProduct(Long sizeProductId) {
        this.sizeProductId = sizeProductId;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public void setSizeProductId(Long sizeProductId) {
        this.sizeProductId = sizeProductId;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(String replyComment) {
        this.replyComment = replyComment;
    }
}
