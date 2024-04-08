package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.BrandProduct;
import com.example.h2_shop.model.Roles;
import com.example.h2_shop.model.dto.BrandProductDTO;
import com.example.h2_shop.model.dto.RolesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandProductMapper extends EntityMapper<BrandProductDTO, BrandProduct> {
}
