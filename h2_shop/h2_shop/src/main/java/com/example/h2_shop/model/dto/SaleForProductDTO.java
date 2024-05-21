package com.example.h2_shop.model.dto;

import java.time.Instant;
import java.util.List;

public class SaleForProductDTO {
    private String code;
    private Float maxPurchase;
    private Instant startDate;
    private Instant endDate;
    private List<ProductBestSellerDTO> lstProductBestSale;

    public SaleForProductDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getMaxPurchase() {
        return maxPurchase;
    }

    public void setMaxPurchase(Float maxPurchase) {
        this.maxPurchase = maxPurchase;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public List<ProductBestSellerDTO> getLstProductBestSale() {
        return lstProductBestSale;
    }

    public void setLstProductBestSale(List<ProductBestSellerDTO> lstProductBestSale) {
        this.lstProductBestSale = lstProductBestSale;
    }
}
