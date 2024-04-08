package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Product;
import com.example.h2_shop.model.Roles;
import com.example.h2_shop.model.dto.ProductDTO;
import com.example.h2_shop.model.dto.RolesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
}
