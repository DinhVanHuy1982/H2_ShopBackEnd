package com.example.h2_shop.model.dto;

import java.util.List;

public class DiagramDetailDTO {
    private List<Double> dataTurnover;
    private List<Double> dataspend;

    public DiagramDetailDTO() {
    }

    public List<Double> getDataTurnover() {
        return dataTurnover;
    }

    public void setDataTurnover(List<Double> dataTurnover) {
        this.dataTurnover = dataTurnover;
    }

    public List<Double> getDataspend() {
        return dataspend;
    }

    public void setDataspend(List<Double> dataspend) {
        this.dataspend = dataspend;
    }
}
