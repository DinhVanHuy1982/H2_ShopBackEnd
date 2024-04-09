package com.example.h2_shop.model.dto;

import com.example.h2_shop.model.Roles;
import lombok.Data;
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

    private Roles roles;

    private Long roleId;

//    private GrantedAuthority grantedAuthority;


    public UserDto(Long id, String username, String password, String email, String address, String phoneNumber, String avatar, Roles roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.roles = roles;
//        this.grantedAuthority = grantedAuthority;
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

    //    public GrantedAuthority getGrantedAuthority() {
//        return grantedAuthority;
//    }
//
//    public void setGrantedAuthority(GrantedAuthority grantedAuthority) {
//        this.grantedAuthority = grantedAuthority;
//    }
}
