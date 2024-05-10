package com.example.h2_shop.model.dto;

public class ProductSearchResponse {
    private Long id;
    private String productCode;
    private String productName;
    private Double price;
    private Double priceSale;
    private String fileName;
    private Double purchase;
    private Long buyQuantity;
    private Double avgRating;

    public ProductSearchResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }



    public Long getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Long buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Double getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(Double priceSale) {
        this.priceSale = priceSale;
    }

    public Double getPurchase() {
        return purchase;
    }

    public void setPurchase(Double purchase) {
        this.purchase = purchase;
    }
}
