package com.example.h2_shop.model.dto;

import java.util.List;

public class ProductDetailResponseDTO {
    private Long id;
    private String productCode;
    private String productName;
    private Long brandId;
    private Long categoriesID;
    private Long price;
    private String description;
    private Double avgRate;
    private Long totalSold;
    private Integer rate1;
    private Integer rate2;
    private Integer rate3;
    private Integer rate4;
    private Integer rate5;

    private List<ProductImgDTO> lstProductIMG;
    private List<String> pathImg;
    private List<SizeDTO> sizeDTOS;
    private List<TypeProductDTO> typeProductDTOS;
    private List<TypeSizeDTO> typeSizeDTOS;
    private List<TypeSizeDTO> listProductDetail;
    private List<Long> imgDelete;

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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoriesID() {
        return categoriesID;
    }

    public void setCategoriesID(Long categoriesID) {
        this.categoriesID = categoriesID;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPathImg() {
        return pathImg;
    }

    public void setPathImg(List<String> pathImg) {
        this.pathImg = pathImg;
    }

    public List<SizeDTO> getSizeDTOS() {
        return sizeDTOS;
    }

    public void setSizeDTOS(List<SizeDTO> sizeDTOS) {
        this.sizeDTOS = sizeDTOS;
    }

    public List<TypeProductDTO> getTypeProductDTOS() {
        return typeProductDTOS;
    }

    public void setTypeProductDTOS(List<TypeProductDTO> typeProductDTOS) {
        this.typeProductDTOS = typeProductDTOS;
    }

    public List<TypeSizeDTO> getTypeSizeDTOS() {
        return typeSizeDTOS;
    }

    public void setTypeSizeDTOS(List<TypeSizeDTO> typeSizeDTOS) {
        this.typeSizeDTOS = typeSizeDTOS;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
    }

    public Long getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(Long totalSold) {
        this.totalSold = totalSold;
    }

    public List<ProductImgDTO> getLstProductIMG() {
        return lstProductIMG;
    }

    public void setLstProductIMG(List<ProductImgDTO> lstProductIMG) {
        this.lstProductIMG = lstProductIMG;
    }

    public Integer getRate1() {
        return rate1;
    }

    public void setRate1(Integer rate1) {
        this.rate1 = rate1;
    }

    public Integer getRate2() {
        return rate2;
    }

    public void setRate2(Integer rate2) {
        this.rate2 = rate2;
    }

    public Integer getRate3() {
        return rate3;
    }

    public void setRate3(Integer rate3) {
        this.rate3 = rate3;
    }

    public Integer getRate4() {
        return rate4;
    }

    public void setRate4(Integer rate4) {
        this.rate4 = rate4;
    }

    public Integer getRate5() {
        return rate5;
    }

    public void setRate5(Integer rate5) {
        this.rate5 = rate5;
    }

    public List<TypeSizeDTO> getListProductDetail() {
        return listProductDetail;
    }

    public void setListProductDetail(List<TypeSizeDTO> listProductDetail) {
        this.listProductDetail = listProductDetail;
    }

    public List<Long> getImgDelete() {
        return imgDelete;
    }

    public void setImgDelete(List<Long> imgDelete) {
        this.imgDelete = imgDelete;
    }
}
