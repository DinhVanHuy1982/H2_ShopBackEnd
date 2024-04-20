package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.CartDTO;
import com.example.h2_shop.service.CartService;
import com.example.h2_shop.service.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    @Autowired
    CartService cartService;
    @PostMapping("/create/cart")
    public ServiceResult<CartDTO> addToCart(@RequestBody  CartDTO cartDTO){
        log.info("Create or add cart ");
        ServiceResult<CartDTO> serviceResult = new ServiceResult<>();
        return this.cartService.createCart(cartDTO);
    }
}
