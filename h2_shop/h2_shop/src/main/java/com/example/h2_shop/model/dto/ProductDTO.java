package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.Categories;
import com.example.h2_shop.model.ProductDetail;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

public class ProductDTO {
    private Long id;
    private String productCode;
    private String productName;
    private Long price;
    private Long quantity;
    private String description;
    private Instant createTime;
    private Instant updateTime;
    private String typeWarranty;
    private Long warranty;

    private Float tax;
    private String saleCode;
    private Categories categories;

    private List<SizeDTO> sizeDTOList;

    private List<TypeProductDTO> typeProductDTOList;

    private Long brandId;

    private Long categoriesID;
    private boolean selected;

    private List<ProductImgDTO> productImgDTOList;

    private BrandProductDTO brandProductDTO;

    private List<ProductDetailDTO> listProductDetail;
    public BrandProductDTO getBrandProductDTO() {
        return brandProductDTO;
    }

    public void setBrandProductDTO(BrandProductDTO brandProductDTO) {
        this.brandProductDTO = brandProductDTO;
    }

    public List<ProductImgDTO> getProductImgDTOList() {
        return productImgDTOList;
    }

    public void setProductImgDTOList(List<ProductImgDTO> productImgDTOList) {
        this.productImgDTOList = productImgDTOList;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public List<SizeDTO> getSizeDTOList() {
        return sizeDTOList;
    }

    public void setSizeDTOList(List<SizeDTO> sizeDTOList) {
        this.sizeDTOList = sizeDTOList;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public ProductDTO() {
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getTypeWarranty() {
        return typeWarranty;
    }

    public void setTypeWarranty(String typeWarranty) {
        this.typeWarranty = typeWarranty;
    }

    public Long getWarranty() {
        return warranty;
    }

    public void setWarranty(Long warranty) {
        this.warranty = warranty;
    }

    public Long getCategoriesID() {
        return categoriesID;
    }

    public void setCategoriesID(Long categoriesID) {
        this.categoriesID = categoriesID;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    public List<ProductDetailDTO> getListProductDetail() {
        return listProductDetail;
    }

    public void setListProductDetail(List<ProductDetailDTO> listProductDetail) {
        this.listProductDetail = listProductDetail;
    }

    public List<TypeProductDTO> getTypeProductDTOList() {
        return typeProductDTOList;
    }

    public void setTypeProductDTOList(List<TypeProductDTO> typeProductDTOList) {
        this.typeProductDTOList = typeProductDTOList;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
