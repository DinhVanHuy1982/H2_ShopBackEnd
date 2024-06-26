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

    @Column(name="order_date")
    private Instant orderDate;

    @Column(name="payment_method")
    private int paymentMethod;//type_apparams PAYMENT

    @Column(name="type_app")
    private String typeApp;

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
    private Long price;// giá của mỗi sản phẩm được bán ra

    @Column(name="estimatePickUp")
    private String estimatePickUp;

    @Column(name="rating")
    private Long rating;

    @Column(name="tax")
    private float tax;
    @Column(name="shipping_unit")
    private String shippingUnit; // type_apparams SHIPUNIT

    @Column(name="ship_price")
    private float shipPrice;

    @Column(name="sale_code")
    private String saleCode;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

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

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTypeApp() {
        return typeApp;
    }

    public void setTypeApp(String typeApp) {
        this.typeApp = typeApp;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
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

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public String getShippingUnit() {
        return shippingUnit;
    }

    public void setShippingUnit(String shippingUnit) {
        this.shippingUnit = shippingUnit;
    }

    public float getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(float shipPrice) {
        this.shipPrice = shipPrice;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }
}
