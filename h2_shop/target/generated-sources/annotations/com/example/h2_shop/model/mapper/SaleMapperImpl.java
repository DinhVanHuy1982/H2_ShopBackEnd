package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Sale;
import com.example.h2_shop.model.dto.SaleDTO;
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
public class SaleMapperImpl implements SaleMapper {

    @Override
    public Sale toEntity(SaleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Sale sale = new Sale();

        sale.setProduct( dto.getProduct() );
        sale.setId( dto.getId() );
        sale.setQuantity( dto.getQuantity() );
        sale.setDescription( dto.getDescription() );
        sale.setStartTime( dto.getStartTime() );
        sale.setEndTime( dto.getEndTime() );
        sale.setApplyDate( dto.getApplyDate() );
        sale.setType( dto.getType() );
        sale.setMinPrice( dto.getMinPrice() );
        sale.setMaxPrice( dto.getMaxPrice() );
        sale.setMaxDiscount( dto.getMaxDiscount() );
        sale.setMaxPurchase( dto.getMaxPurchase() );

        return sale;
    }

    @Override
    public SaleDTO toDto(Sale entity) {
        if ( entity == null ) {
            return null;
        }

        SaleDTO saleDTO = new SaleDTO();

        saleDTO.setProduct( entity.getProduct() );
        saleDTO.setId( entity.getId() );
        saleDTO.setQuantity( entity.getQuantity() );
        saleDTO.setDescription( entity.getDescription() );
        saleDTO.setStartTime( entity.getStartTime() );
        saleDTO.setEndTime( entity.getEndTime() );
        saleDTO.setApplyDate( entity.getApplyDate() );
        saleDTO.setType( entity.getType() );
        saleDTO.setMinPrice( entity.getMinPrice() );
        saleDTO.setMaxPrice( entity.getMaxPrice() );
        saleDTO.setMaxDiscount( entity.getMaxDiscount() );
        saleDTO.setMaxPurchase( entity.getMaxPurchase() );

        return saleDTO;
    }

    @Override
    public List<Sale> toEntity(List<SaleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Sale> list = new ArrayList<Sale>( dtoList.size() );
        for ( SaleDTO saleDTO : dtoList ) {
            list.add( toEntity( saleDTO ) );
        }

        return list;
    }

    @Override
    public List<SaleDTO> toDto(List<Sale> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SaleDTO> list = new ArrayList<SaleDTO>( entityList.size() );
        for ( Sale sale : entityList ) {
            list.add( toDto( sale ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Sale entity, SaleDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getProduct() != null ) {
            entity.setProduct( dto.getProduct() );
        }
        entity.setId( dto.getId() );
        if ( dto.getQuantity() != null ) {
            entity.setQuantity( dto.getQuantity() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getStartTime() != null ) {
            entity.setStartTime( dto.getStartTime() );
        }
        if ( dto.getEndTime() != null ) {
            entity.setEndTime( dto.getEndTime() );
        }
        if ( dto.getApplyDate() != null ) {
            entity.setApplyDate( dto.getApplyDate() );
        }
        if ( dto.getType() != null ) {
            entity.setType( dto.getType() );
        }
        entity.setMinPrice( dto.getMinPrice() );
        entity.setMaxPrice( dto.getMaxPrice() );
        entity.setMaxDiscount( dto.getMaxDiscount() );
        entity.setMaxPurchase( dto.getMaxPurchase() );
    }
}
