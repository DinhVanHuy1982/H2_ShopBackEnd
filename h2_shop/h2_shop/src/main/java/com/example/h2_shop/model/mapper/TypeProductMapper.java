package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.TypeProduct;
import com.example.h2_shop.model.dto.TypeProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeProductMapper extends EntityMapper<TypeProductDTO, TypeProduct> {
}
