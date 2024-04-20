package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.Notify;
import com.example.h2_shop.model.dto.NotifyDTO;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper(componentModel = "spring")
public interface NotifyMapper extends EntityMapper<NotifyDTO, Notify> {
}
