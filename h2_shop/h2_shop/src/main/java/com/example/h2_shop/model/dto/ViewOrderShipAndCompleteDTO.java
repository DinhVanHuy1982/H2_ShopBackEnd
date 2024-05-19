package com.example.h2_shop.model.dto;

import java.time.Instant;

public class ViewOrderShipAndCompleteDTO {

    private Long id;
    private String orderCode;
    private Instant orderDate;
    private String phoneNumber;
    private Float price;
    private Float shipPrice;
    private Long status;
    private Long paymentMethod;
    private Long payStatus;

    public ViewOrderShipAndCompleteDTO(Long id, String orderCode, Instant orderDate, String phoneNumber, Float price, Float shipPrice, Long status, Long paymentMethod, Long payStatus) {
        this.id = id;
        this.orderCode = orderCode;
        this.orderDate = orderDate;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.shipPrice = shipPrice;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.payStatus = payStatus;
    }

    public ViewOrderShipAndCompleteDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(Float shipPrice) {
        this.shipPrice = shipPrice;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Long paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Long payStatus) {
        this.payStatus = payStatus;
    }
}
