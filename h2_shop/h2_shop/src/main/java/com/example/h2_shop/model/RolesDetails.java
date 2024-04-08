package com.example.h2_shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role_details")
public class RolesDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;

    @ManyToOne
    @JoinColumn(name="function_id")
    private Function function;

    @Column(name="action")
    private String action;

    public RolesDetails() {
    }

    public RolesDetails(long id, Roles role, Function function, String action) {
        this.id = id;
        this.role = role;
        this.function = function;
        this.action = action;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
