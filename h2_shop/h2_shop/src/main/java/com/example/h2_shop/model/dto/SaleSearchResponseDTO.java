package com.example.h2_shop.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;


public class SaleSearchResponseDTO {
    private int page;
    private int pageSize;
    private String keySearch;
    private String code;
    private String name;
    private String type;
    private Long quantity;
    private Instant startTime;
    private Instant endTime;
    private String lstProductStr;
    private List<ProductDTO> productDTOList;
    private Instant applySearch;
    private Float maxPurchase;

    public SaleSearchResponseDTO() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getLstProductStr() {
        return lstProductStr;
    }

    public void setLstProductStr(String lstProductStr) {
        this.lstProductStr = lstProductStr;
    }

    public List<ProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public void setProductDTOList(List<ProductDTO> productDTOList) {
        this.productDTOList = productDTOList;
    }

    public Instant getApplySearch() {
        return applySearch;
    }

    public void setApplySearch(Instant applySearch) {
        this.applySearch = applySearch;
    }

    public Float getMaxPurchase() {
        return maxPurchase;
    }

    public void setMaxPurchase(Float maxPurchase) {
        this.maxPurchase = maxPurchase;
    }
}
