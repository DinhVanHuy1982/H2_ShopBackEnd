package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Function;
import com.example.h2_shop.model.dto.FunctionsDTO;
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
public class FunctionMapperImpl implements FunctionMapper {

    @Override
    public Function toEntity(FunctionsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Function function = new Function();

        if ( dto.getId() != null ) {
            function.setId( dto.getId() );
        }

        return function;
    }

    @Override
    public FunctionsDTO toDto(Function entity) {
        if ( entity == null ) {
            return null;
        }

        FunctionsDTO functionsDTO = new FunctionsDTO();

        functionsDTO.setId( entity.getId() );

        return functionsDTO;
    }

    @Override
    public List<Function> toEntity(List<FunctionsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Function> list = new ArrayList<Function>( dtoList.size() );
        for ( FunctionsDTO functionsDTO : dtoList ) {
            list.add( toEntity( functionsDTO ) );
        }

        return list;
    }

    @Override
    public List<FunctionsDTO> toDto(List<Function> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FunctionsDTO> list = new ArrayList<FunctionsDTO>( entityList.size() );
        for ( Function function : entityList ) {
            list.add( toDto( function ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Function entity, FunctionsDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
    }
}
