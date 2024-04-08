package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.User;
import com.example.h2_shop.model.dto.UserDto;
import org.mapstruct.*;
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto,User >{
}
