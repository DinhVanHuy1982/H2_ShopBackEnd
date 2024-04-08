package com.example.h2_shop.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "categories")
public class Categories {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "categori_name")
    private String categoriName;
    @Column(name = "categori_code")
    private String categoriCode;
    @Column(name = "parent_id")
    private long parentId;
    @Column(name = "create_time")
    private Instant createTime;
    @Column(name = "description")
    private String description;

    public Categories() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoriName() {
        return categoriName;
    }

    public void setCategoriName(String categoriName) {
        this.categoriName = categoriName;
    }

    public String getCategoriCode() {
        return categoriCode;
    }

    public void setCategoriCode(String categoriCode) {
        this.categoriCode = categoriCode;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
