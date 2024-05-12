package com.example.h2_shop.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class OrderViewDetailDTO {
    private Long id;
    private String code;
    private String fullName;
    private String phoneNumber;
    private Float price;
    private Float shipPrice;
    private Long provinceId;
    private Long districtId;
    private String ward;
    private Long shippingUnit;
    private Long payStatus;
    private Long paymentMethod;
    private Long status;
    private String orderDetailConcat;
    private Integer typeSale;
    private String saleName;
    private List<OrderDetailForCartDTO> orderDetailForCartDTOS;

    public OrderViewDetailDTO() {
    }

    public OrderViewDetailDTO(Long id, String code, String fullName, String phoneNumber, Long provinceId, Long districtId, String ward, Long shippingUnit, Long payStatus, Long status, String orderDetailConcat, Integer typeSale, String saleName, List<OrderDetailForCartDTO> orderDetailForCartDTOS) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.ward = ward;
        this.shippingUnit = shippingUnit;
        this.payStatus = payStatus;
        this.status = status;
        this.orderDetailConcat = orderDetailConcat;
        this.typeSale = typeSale;
        this.saleName = saleName;
        this.orderDetailForCartDTOS = orderDetailForCartDTOS;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public Long getShippingUnit() {
        return shippingUnit;
    }

    public void setShippingUnit(Long shippingUnit) {
        this.shippingUnit = shippingUnit;
    }

    public Long getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Long payStatus) {
        this.payStatus = payStatus;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getOrderDetailConcat() {
        return orderDetailConcat;
    }

    public void setOrderDetailConcat(String orderDetailConcat) {
        this.orderDetailConcat = orderDetailConcat;
    }

    public Integer getTypeSale() {
        return typeSale;
    }

    public void setTypeSale(Integer typeSale) {
        this.typeSale = typeSale;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public List<OrderDetailForCartDTO> getOrderDetailForCartDTOS() {
        return orderDetailForCartDTOS;
    }

    public void setOrderDetailForCartDTOS(List<OrderDetailForCartDTO> orderDetailForCartDTOS) {
        this.orderDetailForCartDTOS = orderDetailForCartDTOS;
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

    public Long getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Long paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
