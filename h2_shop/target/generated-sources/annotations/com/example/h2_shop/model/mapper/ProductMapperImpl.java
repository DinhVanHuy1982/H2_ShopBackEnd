package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Product;
import com.example.h2_shop.model.dto.ProductDTO;
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
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Product product = new Product();

        product.setCategories( dto.getCategories() );
        product.setId( dto.getId() );
        product.setProductCode( dto.getProductCode() );
        product.setProductName( dto.getProductName() );
        product.setPrice( dto.getPrice() );
        product.setQuantity( dto.getQuantity() );
        product.setDescription( dto.getDescription() );
        product.setCreateTime( dto.getCreateTime() );
        product.setUpdateTime( dto.getUpdateTime() );
        product.setTypeWarranty( dto.getTypeWarranty() );
        product.setWarranty( dto.getWarranty() );

        return product;
    }

    @Override
    public ProductDTO toDto(Product entity) {
        if ( entity == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setCategories( entity.getCategories() );
        productDTO.setId( entity.getId() );
        productDTO.setProductCode( entity.getProductCode() );
        productDTO.setProductName( entity.getProductName() );
        productDTO.setPrice( entity.getPrice() );
        productDTO.setQuantity( entity.getQuantity() );
        productDTO.setDescription( entity.getDescription() );
        productDTO.setCreateTime( entity.getCreateTime() );
        productDTO.setUpdateTime( entity.getUpdateTime() );
        productDTO.setTypeWarranty( entity.getTypeWarranty() );
        productDTO.setWarranty( entity.getWarranty() );

        return productDTO;
    }

    @Override
    public List<Product> toEntity(List<ProductDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( dtoList.size() );
        for ( ProductDTO productDTO : dtoList ) {
            list.add( toEntity( productDTO ) );
        }

        return list;
    }

    @Override
    public List<ProductDTO> toDto(List<Product> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( entityList.size() );
        for ( Product product : entityList ) {
            list.add( toDto( product ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Product entity, ProductDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCategories() != null ) {
            entity.setCategories( dto.getCategories() );
        }
        entity.setId( dto.getId() );
        if ( dto.getProductCode() != null ) {
            entity.setProductCode( dto.getProductCode() );
        }
        if ( dto.getProductName() != null ) {
            entity.setProductName( dto.getProductName() );
        }
        entity.setPrice( dto.getPrice() );
        entity.setQuantity( dto.getQuantity() );
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getCreateTime() != null ) {
            entity.setCreateTime( dto.getCreateTime() );
        }
        if ( dto.getUpdateTime() != null ) {
            entity.setUpdateTime( dto.getUpdateTime() );
        }
        if ( dto.getTypeWarranty() != null ) {
            entity.setTypeWarranty( dto.getTypeWarranty() );
        }
        entity.setWarranty( dto.getWarranty() );
    }
}
