package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Brands;
import com.example.h2_shop.model.dto.BrandsDTO;
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
public class BrandMapperImpl implements BrandMapper {

    @Override
    public Brands toEntity(BrandsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Brands brands = new Brands();

        brands.setId( dto.getId() );
        brands.setBrandName( dto.getBrandName() );
        brands.setBrandCode( dto.getBrandCode() );
        brands.setDescription( dto.getDescription() );
        brands.setCreateTime( dto.getCreateTime() );
        brands.setAddress( dto.getAddress() );
        brands.setPhoneNumber( dto.getPhoneNumber() );
        brands.setEmail( dto.getEmail() );
        brands.setAvatar( dto.getAvatar() );

        return brands;
    }

    @Override
    public BrandsDTO toDto(Brands entity) {
        if ( entity == null ) {
            return null;
        }

        BrandsDTO brandsDTO = new BrandsDTO();

        brandsDTO.setId( entity.getId() );
        brandsDTO.setBrandName( entity.getBrandName() );
        brandsDTO.setBrandCode( entity.getBrandCode() );
        brandsDTO.setDescription( entity.getDescription() );
        brandsDTO.setCreateTime( entity.getCreateTime() );
        brandsDTO.setAddress( entity.getAddress() );
        brandsDTO.setPhoneNumber( entity.getPhoneNumber() );
        brandsDTO.setEmail( entity.getEmail() );
        brandsDTO.setAvatar( entity.getAvatar() );

        return brandsDTO;
    }

    @Override
    public List<Brands> toEntity(List<BrandsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Brands> list = new ArrayList<Brands>( dtoList.size() );
        for ( BrandsDTO brandsDTO : dtoList ) {
            list.add( toEntity( brandsDTO ) );
        }

        return list;
    }

    @Override
    public List<BrandsDTO> toDto(List<Brands> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BrandsDTO> list = new ArrayList<BrandsDTO>( entityList.size() );
        for ( Brands brands : entityList ) {
            list.add( toDto( brands ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Brands entity, BrandsDTO dto) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        if ( dto.getBrandName() != null ) {
            entity.setBrandName( dto.getBrandName() );
        }
        if ( dto.getBrandCode() != null ) {
            entity.setBrandCode( dto.getBrandCode() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getCreateTime() != null ) {
            entity.setCreateTime( dto.getCreateTime() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( dto.getAddress() );
        }
        if ( dto.getPhoneNumber() != null ) {
            entity.setPhoneNumber( dto.getPhoneNumber() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getAvatar() != null ) {
            entity.setAvatar( dto.getAvatar() );
        }
    }
}
