package com.example.h2_shop.model.dto;

public class ProductBestSellerDTO {
    private Long id;
    private String productName;
    private String avatar;
    private String productCode;
    private Long price;
    private Long priceSale;
    private String categoriCode;
    private String categorieName;
    private Long quantityHave;
    private Long orderQuantity;
    private float ratingAvg;

    public ProductBestSellerDTO() {
    }

    public ProductBestSellerDTO(Long id, String productName, String productCode, Long price, Long priceSale, String categoriCode, String categorieName, Long quantityHave, Long orderQuantity, float ratingAvg) {
        this.id = id;
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.priceSale = priceSale;
        this.categoriCode = categoriCode;
        this.categorieName = categorieName;
        this.quantityHave = quantityHave;
        this.orderQuantity = orderQuantity;
        this.ratingAvg = ratingAvg;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(Long priceSale) {
        this.priceSale = priceSale;
    }

    public String getCategoriCode() {
        return categoriCode;
    }

    public void setCategoriCode(String categoriCode) {
        this.categoriCode = categoriCode;
    }

    public String getCategorieName() {
        return categorieName;
    }

    public void setCategorieName(String categorieName) {
        this.categorieName = categorieName;
    }

    public Long getQuantityHave() {
        return quantityHave;
    }

    public void setQuantityHave(Long quantityHave) {
        this.quantityHave = quantityHave;
    }

    public Long getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Long orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public float getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(float ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
