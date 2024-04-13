package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.Orders;
import com.example.h2_shop.model.Product;
import com.example.h2_shop.model.ProductDetail;

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
}
