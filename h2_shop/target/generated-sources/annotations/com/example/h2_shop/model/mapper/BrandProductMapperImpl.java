package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.BrandProduct;
import com.example.h2_shop.model.dto.BrandProductDTO;
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
public class BrandProductMapperImpl implements BrandProductMapper {

    @Override
    public BrandProduct toEntity(BrandProductDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BrandProduct brandProduct = new BrandProduct();

        brandProduct.setBrands( dto.getBrands() );
        brandProduct.setProduct( dto.getProduct() );
        brandProduct.setId( dto.getId() );
        brandProduct.setImportQuantity( dto.getImportQuantity() );
        brandProduct.setImportPrice( dto.getImportPrice() );
        brandProduct.setImportDate( dto.getImportDate() );
        brandProduct.setCategoryCode( dto.getCategoryCode() );

        return brandProduct;
    }

    @Override
    public BrandProductDTO toDto(BrandProduct entity) {
        if ( entity == null ) {
            return null;
        }

        BrandProductDTO brandProductDTO = new BrandProductDTO();

        brandProductDTO.setBrands( entity.getBrands() );
        brandProductDTO.setProduct( entity.getProduct() );
        brandProductDTO.setId( entity.getId() );
        brandProductDTO.setImportQuantity( entity.getImportQuantity() );
        brandProductDTO.setImportPrice( entity.getImportPrice() );
        brandProductDTO.setImportDate( entity.getImportDate() );
        brandProductDTO.setCategoryCode( entity.getCategoryCode() );

        return brandProductDTO;
    }

    @Override
    public List<BrandProduct> toEntity(List<BrandProductDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<BrandProduct> list = new ArrayList<BrandProduct>( dtoList.size() );
        for ( BrandProductDTO brandProductDTO : dtoList ) {
            list.add( toEntity( brandProductDTO ) );
        }

        return list;
    }

    @Override
    public List<BrandProductDTO> toDto(List<BrandProduct> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BrandProductDTO> list = new ArrayList<BrandProductDTO>( entityList.size() );
        for ( BrandProduct brandProduct : entityList ) {
            list.add( toDto( brandProduct ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(BrandProduct entity, BrandProductDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getBrands() != null ) {
            entity.setBrands( dto.getBrands() );
        }
        if ( dto.getProduct() != null ) {
            entity.setProduct( dto.getProduct() );
        }
        entity.setId( dto.getId() );
        entity.setImportQuantity( dto.getImportQuantity() );
        entity.setImportPrice( dto.getImportPrice() );
        if ( dto.getImportDate() != null ) {
            entity.setImportDate( dto.getImportDate() );
        }
        if ( dto.getCategoryCode() != null ) {
            entity.setCategoryCode( dto.getCategoryCode() );
        }
    }
}
