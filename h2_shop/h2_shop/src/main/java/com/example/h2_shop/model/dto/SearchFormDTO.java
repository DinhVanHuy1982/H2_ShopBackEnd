package com.example.h2_shop.model.dto;

public class SearchFormDTO {
    private Long id;
    private Integer page;
    private Integer pageSize;
    private String propertiesSort;
    private String sort;
    private String keySearch;
    private Long status;

    public SearchFormDTO() {
    }

    public SearchFormDTO(Long id, Integer page, Integer pageSize, String propertiesSort, String sort, String keySearch, Long status) {
        this.id = id;
        this.page = page;
        this.pageSize = pageSize;
        this.propertiesSort = propertiesSort;
        this.sort = sort;
        this.keySearch = keySearch;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getPropertiesSort() {
        return propertiesSort;
    }

    public void setPropertiesSort(String propertiesSort) {
        this.propertiesSort = propertiesSort;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
