package com.example.h2_shop.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="userName")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;

    @Column(name="province_id")
    private Long  provinceID;

    @Column(name="district_id")
    private Long districtID;

    @Column(name="ward_code")
    private String wardCode;

    @Column(name="address")
    private String address;

    @Column(name="phoneNumber")
    private String phoneNumber;

    @Column(name="avatar")
    private String avatar;

    @Column(name="file_id")
    private String fileId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles roles;

    @Column(name = "is_active")
    private Long isActive;

    @Column(name="create_time")
    private Instant createTime;

    @Column(name="status")
    private Long status;
    @Column(name = "reset_count")
    private Integer resetCount;
    @Column(name = "code_reset")
    private Long codeReset;
    @Column(name = "reset_date")
    private Instant resetDate;

    @Column(name="full_name")
    private String fullName;

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public User(){

    }

    public User(Long id, String username, String password, String email, String address, String phoneNumber, String avatar, String fileId, Roles roles, Long isActive, Instant createTime, Long status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.fileId = fileId;
        this.roles = roles;
        this.isActive = isActive;
        this.createTime = createTime;
        this.status = status;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getIsActive() {
        return isActive;
    }

    public void setIsActive(Long isActive) {
        this.isActive = isActive;
    }

    public Instant getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Integer getResetCount() {
        return resetCount;
    }

    public void setResetCount(Integer resetCount) {
        this.resetCount = resetCount;
    }

    public Long getCodeReset() {
        return codeReset;
    }

    public void setCodeReset(Long codeReset) {
        this.codeReset = codeReset;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
