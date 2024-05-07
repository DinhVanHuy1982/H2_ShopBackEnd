package com.example.h2_shop.model.dto;


public class ProductRequestDTO {
    private Long id;
    private String nameSearch;
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
}
