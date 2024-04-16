package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.CartDTO;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CartController {

    @PostMapping("/create/cart")
    public ServiceResult<CartDTO> addToCart(CartDTO cartDTO){
        ServiceResult<CartDTO> serviceResult = new ServiceResult<>();
        return serviceResult;
    }
}
