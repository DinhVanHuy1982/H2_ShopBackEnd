package com.example.h2_shop.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.laos.edu.domain.Functions} entity.
 */
public class FunctionsDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String nameEn;

    private String nameJp;
    private List<ActionsDTO> listActions;
    private List<Integer> listActionSelected;
    private boolean selected;
    private boolean selectedAll;

    private String nameEN;

    private String nameJP;

    public List<ActionsDTO> getListActions() {
        return listActions;
    }

    public void setListActions(List<ActionsDTO> listActions) {
        this.listActions = listActions;
    }

    public List<Integer> getListActionSelected() {
        return listActionSelected;
    }

    public void setListActionSelected(List<Integer> listActionSelected) {
        this.listActionSelected = listActionSelected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelectedAll() {
        return selectedAll;
    }

    public void setSelectedAll(boolean selectedAll) {
        this.selectedAll = selectedAll;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FunctionsDTO)) {
            return false;
        }

        FunctionsDTO functionsDTO = (FunctionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, functionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FunctionsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", nameJp='" + getNameJp() + "'" +
            "}";
    }
}
