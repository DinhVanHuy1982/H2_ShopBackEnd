package com.example.h2_shop.model.mapper;

import com.example.h2_shop.model.ProductDetail;
import com.example.h2_shop.model.dto.ProductDetailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper extends EntityMapper<ProductDetailDTO, ProductDetail> {
}
