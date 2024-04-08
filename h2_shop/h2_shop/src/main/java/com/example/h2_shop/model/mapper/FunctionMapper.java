package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Function;
import com.example.h2_shop.model.Roles;
import com.example.h2_shop.model.dto.FunctionsDTO;
import com.example.h2_shop.model.dto.RolesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FunctionMapper extends EntityMapper<FunctionsDTO, Function> {
}
