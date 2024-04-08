package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.User;
import com.example.h2_shop.repository.UserRepository;
import com.example.h2_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAllUsers();
    }
}
