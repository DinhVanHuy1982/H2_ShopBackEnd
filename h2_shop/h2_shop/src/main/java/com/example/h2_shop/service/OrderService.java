package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.OrdersDTO;

import java.util.List;

public interface OrderService {

    public ServiceResult<OrdersDTO> createOrder(OrdersDTO ordersDTOList);
}
