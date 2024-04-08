package com.example.h2_shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_params")
public class Apprams {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name="code")
    private String code;
    @Column(name="value")
    private String value;
    @Column(name="parent_code")
    private String parentCode;
    @Column(name="type")
    private String type;

    public Apprams() {
    }

    public Apprams(long id, String name, String code, String value, String parentCode, String type) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.value = value;
        this.parentCode = parentCode;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
