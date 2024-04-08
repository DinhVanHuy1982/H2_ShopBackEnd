package com.example.h2_shop.controller;

import com.example.h2_shop.model.User;
import com.example.h2_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/getAll")
    public List<User> getAllUser(){
        return this.userService.getAllUser();
    }
}
