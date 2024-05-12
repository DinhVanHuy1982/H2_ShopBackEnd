package com.example.h2_shop.model.dto;

public class OrderDetailForCartDTO {
    private Long id;
    private String productName;
    private Long quantity;
    private Long price;
    private String sizeName;
    private String typeName;
    private Double priceSale;
    private Long quantityHave;
    private Long productDetailId;

    public OrderDetailForCartDTO() {
    }

    public OrderDetailForCartDTO(Long id, String productName, Long quantity, Long price, String sizeName, String typeName, Double priceSale) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.sizeName = sizeName;
        this.typeName = typeName;
        this.priceSale = priceSale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Double getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(Double priceSale) {
        this.priceSale = priceSale;
    }

    public Long getQuantityHave() {
        return quantityHave;
    }

    public void setQuantityHave(Long quantityHave) {
        this.quantityHave = quantityHave;
    }

    public Long getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(Long productDetailId) {
        this.productDetailId = productDetailId;
    }
}
