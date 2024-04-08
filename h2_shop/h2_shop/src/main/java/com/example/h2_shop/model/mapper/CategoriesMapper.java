package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Categories;
import com.example.h2_shop.model.Roles;
import com.example.h2_shop.model.dto.CategoriesDTO;
import com.example.h2_shop.model.dto.RolesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriesMapper extends EntityMapper<CategoriesDTO, Categories> {
}
