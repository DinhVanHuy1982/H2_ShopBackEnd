package com.example.h2_shop.service;

import com.example.h2_shop.model.Notify;
import com.example.h2_shop.model.User;
import com.example.h2_shop.model.dto.NotifyDTO;
import com.example.h2_shop.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {
    public List<User> getAllUser();

    public ServiceResult<UserDto> createUserNotAvatar(UserDto userDto);
    public ServiceResult<UserDto> createUser(UserDto userDto, MultipartFile avatar);
    public ServiceResult<UserDto> createUserClient(UserDto userDto, MultipartFile avatar);
    public ServiceResult<NotifyDTO> forgotPassword(UserDto userDto) throws MessagingException;
    public ServiceResult<?> confirmPassWord(UserDto userDto);
    public ServiceResult<UserDto> loginUser(UserDto userDto);
}
