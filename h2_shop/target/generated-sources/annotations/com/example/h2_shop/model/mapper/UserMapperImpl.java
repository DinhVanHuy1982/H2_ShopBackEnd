package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Roles;
import com.example.h2_shop.model.User;
import com.example.h2_shop.model.dto.UserDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-05T13:47:13+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.10 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setRoles( dto.getRoles() );
        user.setId( dto.getId() );
        user.setUsername( dto.getUsername() );
        user.setPassword( dto.getPassword() );
        user.setEmail( dto.getEmail() );
        user.setAddress( dto.getAddress() );
        user.setPhoneNumber( dto.getPhoneNumber() );
        user.setAvatar( dto.getAvatar() );

        return user;
    }

    @Override
    public UserDto toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        long id = 0L;
        String username = null;
        String password = null;
        String email = null;
        String address = null;
        String phoneNumber = null;
        String avatar = null;
        Roles roles = null;

        id = entity.getId();
        username = entity.getUsername();
        password = entity.getPassword();
        email = entity.getEmail();
        address = entity.getAddress();
        phoneNumber = entity.getPhoneNumber();
        avatar = entity.getAvatar();
        roles = entity.getRoles();

        UserDto userDto = new UserDto( id, username, password, email, address, phoneNumber, avatar, roles );

        return userDto;
    }

    @Override
    public List<User> toEntity(List<UserDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserDto userDto : dtoList ) {
            list.add( toEntity( userDto ) );
        }

        return list;
    }

    @Override
    public List<UserDto> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(User entity, UserDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getRoles() != null ) {
            entity.setRoles( dto.getRoles() );
        }
        entity.setId( dto.getId() );
        if ( dto.getUsername() != null ) {
            entity.setUsername( dto.getUsername() );
        }
        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( dto.getAddress() );
        }
        if ( dto.getPhoneNumber() != null ) {
            entity.setPhoneNumber( dto.getPhoneNumber() );
        }
        if ( dto.getAvatar() != null ) {
            entity.setAvatar( dto.getAvatar() );
        }
    }
}
