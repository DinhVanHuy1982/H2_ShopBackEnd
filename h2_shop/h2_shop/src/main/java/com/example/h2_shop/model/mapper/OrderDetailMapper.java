package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Function;
import com.example.h2_shop.model.OrderDetail;
import com.example.h2_shop.model.dto.FunctionsDTO;
import com.example.h2_shop.model.dto.OrderDetailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper extends EntityMapper<OrderDetailDTO, OrderDetail> {
}
