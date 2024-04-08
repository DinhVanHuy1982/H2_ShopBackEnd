package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Orders;
import com.example.h2_shop.model.dto.OrdersDTO;
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
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Orders toEntity(OrdersDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Orders orders = new Orders();

        orders.setUser( dto.getUser() );
        orders.setProduct( dto.getProduct() );
        orders.setId( dto.getId() );
        orders.setOrderDate( dto.getOrderDate() );
        orders.setPaymentMethod( dto.getPaymentMethod() );
        orders.setTypeApp( dto.getTypeApp() );
        orders.setPhoneNumber( dto.getPhoneNumber() );
        orders.setRecipientAddress( dto.getRecipientAddress() );
        orders.setBuyerAddress( dto.getBuyerAddress() );
        orders.setQuantity( dto.getQuantity() );
        orders.setStatus( dto.getStatus() );
        orders.setComment( dto.getComment() );
        orders.setPrice( dto.getPrice() );
        orders.setEstimatePickUp( dto.getEstimatePickUp() );
        orders.setRating( dto.getRating() );
        orders.setTax( dto.getTax() );
        orders.setShippingUnit( dto.getShippingUnit() );
        orders.setShipPrice( dto.getShipPrice() );
        orders.setSaleCode( dto.getSaleCode() );

        return orders;
    }

    @Override
    public OrdersDTO toDto(Orders entity) {
        if ( entity == null ) {
            return null;
        }

        OrdersDTO ordersDTO = new OrdersDTO();

        ordersDTO.setUser( entity.getUser() );
        ordersDTO.setProduct( entity.getProduct() );
        ordersDTO.setId( entity.getId() );
        ordersDTO.setOrderDate( entity.getOrderDate() );
        ordersDTO.setPaymentMethod( entity.getPaymentMethod() );
        ordersDTO.setTypeApp( entity.getTypeApp() );
        ordersDTO.setPhoneNumber( entity.getPhoneNumber() );
        ordersDTO.setRecipientAddress( entity.getRecipientAddress() );
        ordersDTO.setBuyerAddress( entity.getBuyerAddress() );
        ordersDTO.setQuantity( entity.getQuantity() );
        ordersDTO.setStatus( entity.getStatus() );
        ordersDTO.setComment( entity.getComment() );
        ordersDTO.setPrice( entity.getPrice() );
        ordersDTO.setEstimatePickUp( entity.getEstimatePickUp() );
        ordersDTO.setRating( entity.getRating() );
        ordersDTO.setTax( entity.getTax() );
        ordersDTO.setShippingUnit( entity.getShippingUnit() );
        ordersDTO.setShipPrice( entity.getShipPrice() );
        ordersDTO.setSaleCode( entity.getSaleCode() );

        return ordersDTO;
    }

    @Override
    public List<Orders> toEntity(List<OrdersDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Orders> list = new ArrayList<Orders>( dtoList.size() );
        for ( OrdersDTO ordersDTO : dtoList ) {
            list.add( toEntity( ordersDTO ) );
        }

        return list;
    }

    @Override
    public List<OrdersDTO> toDto(List<Orders> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OrdersDTO> list = new ArrayList<OrdersDTO>( entityList.size() );
        for ( Orders orders : entityList ) {
            list.add( toDto( orders ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Orders entity, OrdersDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getUser() != null ) {
            entity.setUser( dto.getUser() );
        }
        if ( dto.getProduct() != null ) {
            entity.setProduct( dto.getProduct() );
        }
        entity.setId( dto.getId() );
        if ( dto.getOrderDate() != null ) {
            entity.setOrderDate( dto.getOrderDate() );
        }
        entity.setPaymentMethod( dto.getPaymentMethod() );
        if ( dto.getTypeApp() != null ) {
            entity.setTypeApp( dto.getTypeApp() );
        }
        if ( dto.getPhoneNumber() != null ) {
            entity.setPhoneNumber( dto.getPhoneNumber() );
        }
        if ( dto.getRecipientAddress() != null ) {
            entity.setRecipientAddress( dto.getRecipientAddress() );
        }
        if ( dto.getBuyerAddress() != null ) {
            entity.setBuyerAddress( dto.getBuyerAddress() );
        }
        entity.setQuantity( dto.getQuantity() );
        entity.setStatus( dto.getStatus() );
        if ( dto.getComment() != null ) {
            entity.setComment( dto.getComment() );
        }
        entity.setPrice( dto.getPrice() );
        if ( dto.getEstimatePickUp() != null ) {
            entity.setEstimatePickUp( dto.getEstimatePickUp() );
        }
        entity.setRating( dto.getRating() );
        entity.setTax( dto.getTax() );
        if ( dto.getShippingUnit() != null ) {
            entity.setShippingUnit( dto.getShippingUnit() );
        }
        entity.setShipPrice( dto.getShipPrice() );
        if ( dto.getSaleCode() != null ) {
            entity.setSaleCode( dto.getSaleCode() );
        }
    }
}
