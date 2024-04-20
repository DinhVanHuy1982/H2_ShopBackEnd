package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.Roles;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.Instant;
//import org.springframework.security.core.GrantedAuthority;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;

    private String avatar;
    private String fileId;
    private Roles roles;
    private Long isActive;
    private Instant createTime;
    private Long status;
    private Integer resetCount;
    private Long codeReset;
    private Instant resetDate;

    private Long roleId;
    private FileDto fileDto;

//    private GrantedAuthority grantedAuthority;


    public UserDto(Long id, String username, String password, String email, String address, String phoneNumber, String avatar, String fileId, Roles roles, Long isActive, Instant createTime, Long status) {
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

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public FileDto getFileDto() {
        return fileDto;
    }

    public void setFileDto(FileDto fileDto) {
        this.fileDto = fileDto;
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


//    public GrantedAuthority getGrantedAuthority() {
//        return grantedAuthority;
//    }
//
//    public void setGrantedAuthority(GrantedAuthority grantedAuthority) {
//        this.grantedAuthority = grantedAuthority;
//    }
}
