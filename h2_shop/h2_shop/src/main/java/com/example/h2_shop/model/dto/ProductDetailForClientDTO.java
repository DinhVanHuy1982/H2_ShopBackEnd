package com.example.h2_shop.model.dto;

import java.util.List;

public class ProductDetailForClientDTO {
    private Long id;
    private String productCode;
    private String productName;
    private Long brandId;
    private Long categoriesId;
    private Long price;
    private String description;
    private Long totalSold;
    private CommentResponseDTO commentResponseDTO;
    private List<ProductImgDTO> lstProductIMG;
    private List<String> pathImg;
    private List<SizeDTO> sizeDTOS;
    private List<TypeProductDTO> typeProductDTOS;
    private List<TypeSizeDTO> typeSizeDTOS;

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

    public Long getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(Long categoriesId) {
        this.categoriesId = categoriesId;
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

    public CommentResponseDTO getCommentResponseDTO() {
        return commentResponseDTO;
    }

    public void setCommentResponseDTO(CommentResponseDTO commentResponseDTO) {
        this.commentResponseDTO = commentResponseDTO;
    }
}
