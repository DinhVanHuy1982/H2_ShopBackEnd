package com.example.h2_shop.controller;

import com.example.h2_shop.model.User;
import com.example.h2_shop.model.dto.FileDto;
import com.example.h2_shop.model.dto.NotifyDTO;
import com.example.h2_shop.model.dto.UserDto;
import com.example.h2_shop.service.FileService;
import com.example.h2_shop.service.ServiceResult;
import com.example.h2_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    public final FileService fileService;

    @Autowired
    UserService userService;

    public UserController(FileService fileService){
        this.fileService = fileService;
    }
    @GetMapping("/user/getAll")
    public List<User> getAllUser(){
        return this.userService.getAllUser();
    }

    @PostMapping("/user/createUserNotAvatar")
    public ServiceResult<?> createNotAvatar(@RequestBody UserDto userDto){

        ServiceResult<?> serviceResult = this.userService.createUserNotAvatar(userDto);

        return serviceResult;
    }

    @PostMapping(value = "/user/createUser")
    public ServiceResult<?> createUser(@RequestPart(value = "avatar", required = false)MultipartFile avatar, @RequestPart("userDto") UserDto userDto){
        ServiceResult<UserDto> serviceResult = this.userService.createUser(userDto,avatar);
        if(serviceResult.getStatus()== HttpStatus.OK){
//            UserDto userDto1 = serviceResult.getData();
//            ServiceResult<FileDto> fileDtoServiceResult = new ServiceResult<>();
//            if(avatar!=null){
//                fileDtoServiceResult = this.fileService.createFile(avatar);
//            }
//
//            if(fileDtoServiceResult.getStatus() == HttpStatus.OK){
//                userDto1.setFileDto(fileDtoServiceResult.getData());
//                serviceResult.setData(userDto1);
//            }
            return serviceResult;

        }else{
            return serviceResult;
        }
    }

    @PostMapping("/user/forgotPassword")
    public ServiceResult<NotifyDTO> forgotPassword(@RequestBody UserDto userDto) throws MessagingException {
        ServiceResult<NotifyDTO> serviceResult =this.userService.forgotPassword(userDto);
        return serviceResult;
    }

    @PostMapping("/user/confirmResetPass")
    public ServiceResult<?> confirmResetPass(@RequestBody UserDto userDto){
        return this.userService.confirmPassWord(userDto);
    }
}
