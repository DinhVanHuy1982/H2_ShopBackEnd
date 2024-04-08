package com.example.h2_shop.model;

import jakarta.persistence.*;
import jdk.jfr.Name;

@Entity
@Table(name = "actions")
public class Action {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

    public Action() {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
