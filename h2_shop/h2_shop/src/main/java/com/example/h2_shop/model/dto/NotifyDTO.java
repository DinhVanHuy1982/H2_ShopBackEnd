package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.User;
import java.time.Instant;

public class NotifyDTO {
    private Long id;
    private String content;
    private User user;
    private String sendType;
    private String title;
    private Integer resetCount;
    private Instant sendDate;

    public NotifyDTO() {
    }

    public NotifyDTO(Long id, String content, User user, String sendType, String title, Integer resetCount, Instant sendDate) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.sendType = sendType;
        this.title = title;
        this.resetCount = resetCount;
        this.sendDate = sendDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getResetCount() {
        return resetCount;
    }

    public void setResetCount(Integer resetCount) {
        this.resetCount = resetCount;
    }

    public Instant getSendDate() {
        return sendDate;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }
}
