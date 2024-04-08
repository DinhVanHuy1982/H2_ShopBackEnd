package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Size;
import com.example.h2_shop.model.dto.SizeDTO;
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
public class SizeMapperImpl implements SizeMapper {

    @Override
    public Size toEntity(SizeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Size size = new Size();

        size.setProduct( dto.getProduct() );
        size.setId( dto.getId() );
        size.setSizeName( dto.getSizeName() );
        size.setSizeCode( dto.getSizeCode() );
        size.setDescription( dto.getDescription() );
        size.setCreateTime( dto.getCreateTime() );
        size.setUpdateTime( dto.getUpdateTime() );
        size.setCreateName( dto.getCreateName() );
        size.setUpdateName( dto.getUpdateName() );

        return size;
    }

    @Override
    public SizeDTO toDto(Size entity) {
        if ( entity == null ) {
            return null;
        }

        SizeDTO sizeDTO = new SizeDTO();

        sizeDTO.setProduct( entity.getProduct() );
        sizeDTO.setId( entity.getId() );
        sizeDTO.setSizeName( entity.getSizeName() );
        sizeDTO.setSizeCode( entity.getSizeCode() );
        sizeDTO.setDescription( entity.getDescription() );
        sizeDTO.setCreateTime( entity.getCreateTime() );
        sizeDTO.setUpdateTime( entity.getUpdateTime() );
        sizeDTO.setCreateName( entity.getCreateName() );
        sizeDTO.setUpdateName( entity.getUpdateName() );

        return sizeDTO;
    }

    @Override
    public List<Size> toEntity(List<SizeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Size> list = new ArrayList<Size>( dtoList.size() );
        for ( SizeDTO sizeDTO : dtoList ) {
            list.add( toEntity( sizeDTO ) );
        }

        return list;
    }

    @Override
    public List<SizeDTO> toDto(List<Size> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SizeDTO> list = new ArrayList<SizeDTO>( entityList.size() );
        for ( Size size : entityList ) {
            list.add( toDto( size ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Size entity, SizeDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getProduct() != null ) {
            entity.setProduct( dto.getProduct() );
        }
        entity.setId( dto.getId() );
        if ( dto.getSizeName() != null ) {
            entity.setSizeName( dto.getSizeName() );
        }
        if ( dto.getSizeCode() != null ) {
            entity.setSizeCode( dto.getSizeCode() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
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
    }
}
