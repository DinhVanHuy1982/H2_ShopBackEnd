package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Roles;
import com.example.h2_shop.model.dto.RolesDTO;
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
public class RolesMapperImpl implements RolesMapper {

    @Override
    public Roles toEntity(RolesDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Roles roles = new Roles();

        if ( dto.getId() != null ) {
            roles.setId( dto.getId() );
        }
        roles.setCreateTime( dto.getCreateTime() );
        roles.setUpdateTime( dto.getUpdateTime() );
        roles.setCreateName( dto.getCreateName() );
        roles.setUpdateName( dto.getUpdateName() );
        roles.setDescription( dto.getDescription() );

        return roles;
    }

    @Override
    public RolesDTO toDto(Roles entity) {
        if ( entity == null ) {
            return null;
        }

        RolesDTO rolesDTO = new RolesDTO();

        rolesDTO.setId( entity.getId() );
        rolesDTO.setDescription( entity.getDescription() );
        rolesDTO.setCreateTime( entity.getCreateTime() );
        rolesDTO.setCreateName( entity.getCreateName() );
        rolesDTO.setUpdateTime( entity.getUpdateTime() );
        rolesDTO.setUpdateName( entity.getUpdateName() );

        return rolesDTO;
    }

    @Override
    public List<Roles> toEntity(List<RolesDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Roles> list = new ArrayList<Roles>( dtoList.size() );
        for ( RolesDTO rolesDTO : dtoList ) {
            list.add( toEntity( rolesDTO ) );
        }

        return list;
    }

    @Override
    public List<RolesDTO> toDto(List<Roles> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RolesDTO> list = new ArrayList<RolesDTO>( entityList.size() );
        for ( Roles roles : entityList ) {
            list.add( toDto( roles ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Roles entity, RolesDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getCreateTime() != null ) {
            entity.setCreateTime( dto.getCreateTime() );
        }
        if ( dto.getUpdateTime() != null ) {
            entity.setUpdateTime( dto.getUpdateTime() );
        }
        if ( dto.getCreateName() != null ) {
            entity.setCreateName( dto.getCreateName() );
        }
        if ( dto.getUpdateName() != null ) {
            entity.setUpdateName( dto.getUpdateName() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
    }
}
