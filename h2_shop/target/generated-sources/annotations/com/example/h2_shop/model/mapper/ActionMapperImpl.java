package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Action;
import com.example.h2_shop.model.dto.ActionsDTO;
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
public class ActionMapperImpl implements ActionMapper {

    @Override
    public Action toEntity(ActionsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Action action = new Action();

        action.setCode( dto.getCode() );
        action.setName( dto.getName() );
        if ( dto.getId() != null ) {
            action.setId( dto.getId() );
        }

        return action;
    }

    @Override
    public ActionsDTO toDto(Action entity) {
        if ( entity == null ) {
            return null;
        }

        ActionsDTO actionsDTO = new ActionsDTO();

        actionsDTO.setId( entity.getId() );
        actionsDTO.setCode( entity.getCode() );
        actionsDTO.setName( entity.getName() );

        return actionsDTO;
    }

    @Override
    public List<Action> toEntity(List<ActionsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Action> list = new ArrayList<Action>( dtoList.size() );
        for ( ActionsDTO actionsDTO : dtoList ) {
            list.add( toEntity( actionsDTO ) );
        }

        return list;
    }

    @Override
    public List<ActionsDTO> toDto(List<Action> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ActionsDTO> list = new ArrayList<ActionsDTO>( entityList.size() );
        for ( Action action : entityList ) {
            list.add( toDto( action ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Action entity, ActionsDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCode() != null ) {
            entity.setCode( dto.getCode() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
    }
}
