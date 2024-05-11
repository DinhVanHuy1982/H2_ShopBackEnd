package com.example.h2_shop.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name="full_name")
    private String fullName;

    @Column(name="order_date")
    private Instant orderDate;

    @Column(name="payment_method")
    private Integer paymentMethod;//type_apparams PAYMENT

    @Column(name="district_id")
    private Long provinceId;

    @Column(name="province_id")
    private Long districtId;

    @Column(name="ward")
    private String ward;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="recipient_address")
    private String recipientAddress;

    @Column(name="buyerAddress")
    private String buyerAddress;

    @Column(name="quantity")
    private Long quantity;

    @Column(name="status")
    private Long status;

    @Column(name="comment")
    private String comment;

    @Column(name="price")
    private Float price;// giá của tổng hóa đơn

    @Column(name="estimatePickUp")
    private String estimatePickUp;

    @Column(name="rating")
    private Long rating;


    @Column(name="shipping_unit")
    private Long shippingUnit; // type_apparams SHIPUNIT

    @Column(name="ship_price")
    private float shipPrice;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;


//    @ManyToOne
//    @JoinColumn(name="product_id")
//    private Product product;

    @Column(name="reply_comment")
    private String replyComment;

    public String getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(String replyComment) {
        this.replyComment = replyComment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }

    public Orders() {
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

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getEstimatePickUp() {
        return estimatePickUp;
    }

    public void setEstimatePickUp(String estimatePickUp) {
        this.estimatePickUp = estimatePickUp;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getShippingUnit() {
        return shippingUnit;
    }

    public void setShippingUnit(Long shippingUnit) {
        this.shippingUnit = shippingUnit;
    }

    public float getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(float shipPrice) {
        this.shipPrice = shipPrice;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
}
