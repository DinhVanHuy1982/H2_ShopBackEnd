package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.ProductImg;
import com.example.h2_shop.model.dto.ProductImgDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImgMapper extends EntityMapper<ProductImgDTO, ProductImg> {
}
