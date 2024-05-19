package com.example.h2_shop.model.dto;

import java.time.Instant;

public class ImportHistoryDTO {
    private String productName;
    private String brandName;
    private Instant importDate;
    private Long importPrice;
    private Long importQuantity;
    private Long totalImport;

    public ImportHistoryDTO() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Instant getImportDate() {
        return importDate;
    }

    public void setImportDate(Instant importDate) {
        this.importDate = importDate;
    }

    public Long getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(Long importPrice) {
        this.importPrice = importPrice;
    }

    public Long getImportQuantity() {
        return importQuantity;
    }

    public void setImportQuantity(Long importQuantity) {
        this.importQuantity = importQuantity;
    }

    public Long getTotalImport() {
        return totalImport;
    }

    public void setTotalImport(Long totalImport) {
        this.totalImport = totalImport;
    }
}
