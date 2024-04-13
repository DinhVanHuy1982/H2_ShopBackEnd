package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.Product;
import com.example.h2_shop.model.Size;
import com.example.h2_shop.model.TypeProduct;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ProductDetailDTO {
    private Long id;

    private Long quantity;

    private TypeProduct typeProduct;

    private Size size;

    private Product product;

    private Integer positionSize;// vị trí cảu size trong danh sách tạo mới

    private Integer positionType;// vị trí của typeProduct trong danh sách tạo mới


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

    public TypeProduct getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(TypeProduct typeProduct) {
        this.typeProduct = typeProduct;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getPositionSize() {
        return positionSize;
    }

    public void setPositionSize(Integer positionSize) {
        this.positionSize = positionSize;
    }

    public Integer getPositionType() {
        return positionType;
    }

    public void setPositionType(Integer positionType) {
        this.positionType = positionType;
    }
}
