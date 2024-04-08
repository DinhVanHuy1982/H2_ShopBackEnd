package com.example.h2_shop.model.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.laos.edu.domain.Actions} entity.
 */
public class ActionsDTO {

    private Long id;

    private String code;

    private String name;

    private String nameEn;

    private String nameJp;
    private Long selected;

    private String nameEN;

    private String nameJP;

    public Long getSelected() {
        return selected;
    }

    public void setSelected(Long selected) {
        this.selected = selected;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getNameJP() {
        return nameJP;
    }

    public void setNameJP(String nameJP) {
        this.nameJP = nameJP;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameJp() {
        return nameJp;
    }

    public void setNameJp(String nameJp) {
        this.nameJp = nameJp;
    }

}
