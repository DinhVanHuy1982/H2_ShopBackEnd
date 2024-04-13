package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.OrdersDTO;
import com.example.h2_shop.service.OrderService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderService orderService;


    @PostMapping("/create/Order")
    public ServiceResult<OrdersDTO> createOrder(@RequestBody OrdersDTO ordersDTO){

        ServiceResult<OrdersDTO> serviceResult = this.orderService.createOrder(ordersDTO);


        return serviceResult;

    }
}

