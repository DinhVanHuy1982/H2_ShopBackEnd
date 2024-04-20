package com.example.h2_shop.model;

import jakarta.persistence.*;
import org.mapstruct.Mapper;

import java.time.Instant;

@Entity
@Table(name = "notify")
public class Notify {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "send_type")
    private String sendType;

    @Column(name = "title")
    private String title;

    @Column(name = "send_date")
    private Instant sendDate;

    public Notify() {
    }

    public Notify(Long id, String content, User user, String sendType, String title,  Instant sendDate) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.sendType = sendType;
        this.title = title;
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

    public Instant getSendDate() {
        return sendDate;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }
}
