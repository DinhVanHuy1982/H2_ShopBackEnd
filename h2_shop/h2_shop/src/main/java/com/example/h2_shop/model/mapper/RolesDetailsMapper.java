package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Action;
import com.example.h2_shop.model.RolesDetails;
import com.example.h2_shop.model.dto.ActionsDTO;
import com.example.h2_shop.model.dto.RolesDetailsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolesDetailsMapper extends EntityMapper<RolesDetailsDTO, RolesDetails> {
}
