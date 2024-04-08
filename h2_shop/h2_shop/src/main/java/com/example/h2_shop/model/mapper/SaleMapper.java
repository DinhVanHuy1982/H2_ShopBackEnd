package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Roles;
import com.example.h2_shop.model.Sale;
import com.example.h2_shop.model.dto.RolesDTO;
import com.example.h2_shop.model.dto.SaleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleMapper extends EntityMapper<SaleDTO, Sale> {
}
