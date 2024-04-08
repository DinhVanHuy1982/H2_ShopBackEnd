package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Orders;
import com.example.h2_shop.model.Roles;
import com.example.h2_shop.model.dto.OrdersDTO;
import com.example.h2_shop.model.dto.RolesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrdersDTO, Orders> {
}
