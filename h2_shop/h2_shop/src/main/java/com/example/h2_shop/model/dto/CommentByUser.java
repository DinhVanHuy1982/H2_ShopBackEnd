package com.example.h2_shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class CommentByUser {
    private Long userId;
    private String comment;
    private String fullName;
    private Long rating;
    private Instant timeComment;
    private String commentRepply;
    private String avatar;
    private List<String> imgComment;
    private List<String> imgCommentRepply;

    public CommentByUser() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Instant getTimeComment() {
        return timeComment;
    }

    public void setTimeComment(Instant timeComment) {
        this.timeComment = timeComment;
    }

    public String getCommentRepply() {
        return commentRepply;
    }

    public void setCommentRepply(String commentRepply) {
        this.commentRepply = commentRepply;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getImgComment() {
        return imgComment;
    }

    public void setImgComment(List<String> imgComment) {
        this.imgComment = imgComment;
    }

    public List<String> getImgCommentRepply() {
        return imgCommentRepply;
    }

    public void setImgCommentRepply(List<String> imgCommentRepply) {
        this.imgCommentRepply = imgCommentRepply;
    }
}
