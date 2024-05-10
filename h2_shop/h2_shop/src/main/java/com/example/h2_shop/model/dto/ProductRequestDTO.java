package com.example.h2_shop.model.dto;


import java.util.List;

public class ProductRequestDTO {
    private Long id;
    private String nameSearch;
    private Double fromPrice;
    private Double toPrice;
    private String propertySort;
    private List<Integer> star;
    private Long categoriesId;
    private Long brandId;

    private Integer pageSize;
    private Integer page;

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public Long getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(Long categoriesId) {
        this.categoriesId = categoriesId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(Double fromPrice) {
        this.fromPrice = fromPrice;
    }

    public Double getToPrice() {
        return toPrice;
    }

    public void setToPrice(Double toPrice) {
        this.toPrice = toPrice;
    }

    public String getPropertySort() {
        return propertySort;
    }

    public void setPropertySort(String propertySort) {
        this.propertySort = propertySort;
    }

    public List<Integer> getStar() {
        return star;
    }

    public void setStar(List<Integer> star) {
        this.star = star;
    }
}
