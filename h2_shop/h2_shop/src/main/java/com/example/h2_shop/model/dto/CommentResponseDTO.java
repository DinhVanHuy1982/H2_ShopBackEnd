package com.example.h2_shop.model.dto;

import java.util.List;

public class CommentResponseDTO {
    private Long id;
    private Integer rate1;
    private Integer rate2;
    private Integer rate3;
    private Integer rate4;
    private Integer rate5;
    private Integer totalRate;
    private Double avgRate;
    List<CommentByUser> userComment;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Long id, Integer rate1, Integer rate2, Integer rate3, Integer rate4, Integer rate5, Integer totalRate, Double avgRate, List<CommentByUser> userComment) {
        this.id = id;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.rate4 = rate4;
        this.rate5 = rate5;
        this.totalRate = totalRate;
        this.avgRate = avgRate;
        this.userComment = userComment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRate1() {
        return rate1;
    }

    public void setRate1(Integer rate1) {
        this.rate1 = rate1;
    }

    public Integer getRate2() {
        return rate2;
    }

    public void setRate2(Integer rate2) {
        this.rate2 = rate2;
    }

    public Integer getRate3() {
        return rate3;
    }

    public void setRate3(Integer rate3) {
        this.rate3 = rate3;
    }

    public Integer getRate4() {
        return rate4;
    }

    public void setRate4(Integer rate4) {
        this.rate4 = rate4;
    }

    public Integer getRate5() {
        return rate5;
    }

    public void setRate5(Integer rate5) {
        this.rate5 = rate5;
    }

    public Integer getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(Integer totalRate) {
        this.totalRate = totalRate;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
    }

    public List<CommentByUser> getUserComment() {
        return userComment;
    }

    public void setUserComment(List<CommentByUser> userComment) {
        this.userComment = userComment;
    }
}
