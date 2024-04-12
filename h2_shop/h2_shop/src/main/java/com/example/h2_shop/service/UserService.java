package com.example.h2_shop.service;

import com.example.h2_shop.model.User;
import com.example.h2_shop.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    public List<User> getAllUser();

    public ServiceResult<UserDto> createUserNotAvatar(UserDto userDto);
    public ServiceResult<UserDto> createUser(UserDto userDto, MultipartFile avatar);
}
