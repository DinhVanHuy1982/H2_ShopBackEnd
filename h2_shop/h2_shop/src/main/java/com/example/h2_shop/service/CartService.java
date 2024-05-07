package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.CartDTO;

import java.util.List;

public interface CartService {
    public ServiceResult<CartDTO> createCart(CartDTO cartDTO);
    ServiceResult<List<CartDTO>> getCartByUser(Long userId);
}
