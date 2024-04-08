package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Categories;
import com.example.h2_shop.model.dto.CategoriesDTO;
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
public class CategoriesMapperImpl implements CategoriesMapper {

    @Override
    public Categories toEntity(CategoriesDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Categories categories = new Categories();

        categories.setId( dto.getId() );
        categories.setCategoriName( dto.getCategoriName() );
        categories.setCategoriCode( dto.getCategoriCode() );
        categories.setParentId( dto.getParentId() );
        categories.setCreateTime( dto.getCreateTime() );
        categories.setDescription( dto.getDescription() );

        return categories;
    }

    @Override
    public CategoriesDTO toDto(Categories entity) {
        if ( entity == null ) {
            return null;
        }

        CategoriesDTO categoriesDTO = new CategoriesDTO();

        categoriesDTO.setId( entity.getId() );
        categoriesDTO.setCategoriName( entity.getCategoriName() );
        categoriesDTO.setCategoriCode( entity.getCategoriCode() );
        categoriesDTO.setParentId( entity.getParentId() );
        categoriesDTO.setCreateTime( entity.getCreateTime() );
        categoriesDTO.setDescription( entity.getDescription() );

        return categoriesDTO;
    }

    @Override
    public List<Categories> toEntity(List<CategoriesDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Categories> list = new ArrayList<Categories>( dtoList.size() );
        for ( CategoriesDTO categoriesDTO : dtoList ) {
            list.add( toEntity( categoriesDTO ) );
        }

        return list;
    }

    @Override
    public List<CategoriesDTO> toDto(List<Categories> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CategoriesDTO> list = new ArrayList<CategoriesDTO>( entityList.size() );
        for ( Categories categories : entityList ) {
            list.add( toDto( categories ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Categories entity, CategoriesDTO dto) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        if ( dto.getCategoriName() != null ) {
            entity.setCategoriName( dto.getCategoriName() );
        }
        if ( dto.getCategoriCode() != null ) {
            entity.setCategoriCode( dto.getCategoriCode() );
        }
        entity.setParentId( dto.getParentId() );
        if ( dto.getCreateTime() != null ) {
            entity.setCreateTime( dto.getCreateTime() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
    }
}
