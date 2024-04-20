package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.CartDTO;

public interface CartService {
    public ServiceResult<CartDTO> createCart(CartDTO cartDTO);
}
