package com.example.h2_shop.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="create_time")
    private Instant createTime;
    @Column(name="update_time")
    private Instant updateTime;
    @Column(name="create_name")
    private String createName;
    @Column(name="update_name")
    private String updateName;
    @Column(name="description")
    private  String description;
    @Column(name="role_name")
    private String roleName;
    @Column(name="role_code")
    private String roleCode;

    @Column(name = "status")
    private Integer status;

    public Roles() {
    }

    public Roles(Long id, String roleName, String roleCode, Instant createTime, Instant updateTime, String createName, String updateName, String description,Integer status) {
        this.id = id;
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createName = createName;
        this.updateName = updateName;
        this.description = description;
        this.status=status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
