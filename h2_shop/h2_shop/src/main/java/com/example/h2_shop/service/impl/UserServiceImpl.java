package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.Roles;
import com.example.h2_shop.model.User;
import com.example.h2_shop.model.dto.FileDto;
import com.example.h2_shop.model.dto.UserDto;
import com.example.h2_shop.model.mapper.UserMapper;
import com.example.h2_shop.repository.RolesRepository;
import com.example.h2_shop.repository.UserRepository;
import com.example.h2_shop.service.FileService;
import com.example.h2_shop.service.ServiceResult;
import com.example.h2_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    FileService fileService;

    public final RolesRepository rolesRepository;
    public final UserMapper userMapper;
    public UserServiceImpl(UserMapper userMapper, RolesRepository rolesRepository){
        this.rolesRepository=rolesRepository;
        this.userMapper=userMapper;

    }
    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAllUsers();
    }

    @Override
    public ServiceResult<UserDto> createUserNotAvatar(UserDto userDto) {

        ServiceResult<UserDto> serviceResultUser = this.validateUser(userDto);
        if(serviceResultUser.getData()!=null){
            UserDto userDtoVali = serviceResultUser.getData();
            User user = this.userMapper.toEntity(userDtoVali);
            user.setCreateTime(Instant.now());
            user.setStatus(0L);
            user = this.userRepository.save(user);
            UserDto userDtoReturn = this.userMapper.toDto(user);

            ServiceResult<UserDto> userDtoServiceResult = new ServiceResult<>();

//            serviceResultUser.setData(userDtoReturn);
//            serviceResultUser.setMessage("Lưu thành công");
            userDtoServiceResult.setStatus(HttpStatus.OK);
            userDtoServiceResult.setMessage("Lưu thành công");
            userDtoServiceResult.setData(userDtoReturn);
            return userDtoServiceResult;
        }else{
            return serviceResultUser;
        }
    }

    @Override
    public ServiceResult<UserDto> createUser(UserDto userDto, MultipartFile file) {
        ServiceResult<UserDto> serviceResultUser = this.validateUser(userDto); // validate tạo tài khoản
        if(serviceResultUser.getData()!=null){
            UserDto userDtoVali = serviceResultUser.getData();
            User user = this.userMapper.toEntity(userDtoVali);
            user.setCreateTime(Instant.now());
            user.setStatus(0L);

            FileDto fileDto = new FileDto();
            if(file!=null){ // kiểm tra có upload file ảnh đại diện hay không
                ServiceResult<FileDto> fileDtoServiceResult = this.fileService.createFile(file);
                if(fileDtoServiceResult.getStatus()==HttpStatus.OK){
                    user.setAvatar(fileDtoServiceResult.getData().getFileName());
                    fileDto = fileDtoServiceResult.getData();
                }
            }

            user = this.userRepository.save(user);
            UserDto userDtoReturn = this.userMapper.toDto(user);
            userDtoReturn.setFileDto(fileDto);

            ServiceResult<UserDto> userDtoServiceResult = new ServiceResult<>();
            userDtoServiceResult.setStatus(HttpStatus.OK);
            userDtoServiceResult.setMessage("Lưu thành công");
            userDtoServiceResult.setData(userDtoReturn);
            return userDtoServiceResult;
        }else{
            return serviceResultUser;
        }
    }

    public ServiceResult<UserDto> validateUser(UserDto userDto){

        ServiceResult<UserDto> userServiceResult = new ServiceResult<>();
        StringBuilder messError = new StringBuilder();

        //UserName
        if(userDto.getUsername().isEmpty()){
            messError.append("Username không được để trống");
            userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
        }else{
            Optional<User> userCheckUserName = this.userRepository.findByUsername(userDto.getUsername());
            if(userCheckUserName.isPresent()){
                messError.append("Username đã tồn tại");
                userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            }
        }

        //email
        if(userDto.getEmail().isEmpty()){
            userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            messError.append(" Email không được để trống ");
        }else{
            // validate định dạng email
        }

        // roleid
        if(userDto.getRoleId()==null){
            userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            messError.append(" Không được để trống trường role ");
        }else{
            Optional<Roles> roles = this.rolesRepository.findById(userDto.getRoleId());
            if(roles.isEmpty()){
                userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
                messError.append(" Mã role không tồn tại ");
            }else{
                userDto.setRoles(roles.get());
            }
        }

        //password
        if(userDto.getPassword().isEmpty()){
            userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            messError.append(" Mật khẩu Không được để trống");
        }else{
            if(userDto.getPassword().length()<=6){
                userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
                messError.append(" Mật khẩu phải nhiều hơn 6 kí tự ");
            }
        }

        if(userServiceResult.getStatus()!= HttpStatus.BAD_REQUEST){
            userServiceResult.setStatus(HttpStatus.OK);
            userServiceResult.setData(userDto);
        }else{
            userServiceResult.setData(null);
            userServiceResult.setMessage(messError.toString());
        }

        return userServiceResult;
    }
}
