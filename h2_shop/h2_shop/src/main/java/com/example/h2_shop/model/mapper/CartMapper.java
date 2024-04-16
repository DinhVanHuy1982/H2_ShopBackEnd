package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Carts;
import com.example.h2_shop.model.dto.CartDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper extends EntityMapper<CartDTO, Carts> {
}
