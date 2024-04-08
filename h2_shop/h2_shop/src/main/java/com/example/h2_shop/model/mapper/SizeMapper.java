package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Roles;
import com.example.h2_shop.model.Size;
import com.example.h2_shop.model.dto.RolesDTO;
import com.example.h2_shop.model.dto.SizeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SizeMapper extends EntityMapper<SizeDTO, Size> {
}
