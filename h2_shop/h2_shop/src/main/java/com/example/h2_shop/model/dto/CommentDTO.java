package com.example.h2_shop.model.dto;

import java.util.List;

public class CommentDTO {
    private Long productID;
    private Long orderDetailID;
    private Integer rating;
    private String comment;
    private Long userID;
    private List<String> imgCommentList;

    private OrderDetailDTO orderDetailDTO; // trả data ra ngoài

    private List<ProductImgDTO> productImgDTOList;// trả data ra ngoài

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Long getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(Long orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public List<String> getImgCommentList() {
        return imgCommentList;
    }

    public void setImgCommentList(List<String> imgCommentList) {
        this.imgCommentList = imgCommentList;
    }

    public OrderDetailDTO getOrderDetailDTO() {
        return orderDetailDTO;
    }

    public void setOrderDetailDTO(OrderDetailDTO orderDetailDTO) {
        this.orderDetailDTO = orderDetailDTO;
    }

    public List<ProductImgDTO> getProductImgDTOList() {
        return productImgDTOList;
    }

    public void setProductImgDTOList(List<ProductImgDTO> productImgDTOList) {
        this.productImgDTOList = productImgDTOList;
    }
}
