package com.example.h2_shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    @Column(name = "price")
    private Long price;// giá của mỗi sản phẩm

    @Column(name = "comment")
    private String comment;
    @Column(name = "rating")
    private Integer rating;

    @Column(name = "reply_comment")
    private String replyComment;

    public OrderDetail(Long id, Long quantity, Orders orders, ProductDetail productDetail, Long price, String comment, Integer rating, String replyComment) {
        this.id = id;
        this.quantity = quantity;
        this.orders = orders;
        this.productDetail = productDetail;
        this.price = price;
        this.comment = comment;
        this.rating = rating;
        this.replyComment = replyComment;
    }

    public OrderDetail() {
    }

    public OrderDetail(Long id, Long quantity, Orders orders) {
        this.id = id;
        this.quantity = quantity;
        this.orders = orders;
    }

    public OrderDetail(Long id, Long quantity, Orders orders, ProductDetail productDetail, Long price) {
        this.id = id;
        this.quantity = quantity;
        this.orders = orders;
        this.productDetail = productDetail;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(String replyComment) {
        this.replyComment = replyComment;
    }
}
