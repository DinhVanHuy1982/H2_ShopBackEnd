package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.CommentDTO;
import com.example.h2_shop.model.dto.OrdersDTO;
import com.example.h2_shop.service.OrderService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/get-all-order/{userId}")
    public ServiceResult<OrdersDTO> getOrderByUser(@PathVariable Long userId){
        return null;
    }

    /**
     * Description of the method
     *
     * @param imgComments: danh sách các ảnh comment; commentDTO: thông tin comment
     * @return 
     * @throws
     * @author admin
     * @since 4/15/2024
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0
     */
    @PostMapping("/create/comment")
    public ServiceResult<CommentDTO> createComment(@RequestPart(value = "fileComment", required = false)List<MultipartFile> imgComments, @RequestPart("comment")CommentDTO commentDTO){
        return this.orderService.createComment(imgComments,commentDTO);
    }
}

