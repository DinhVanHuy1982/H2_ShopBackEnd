package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.CommentDTO;
import com.example.h2_shop.model.dto.OrderRequestDTO;
import com.example.h2_shop.model.dto.OrderViewDetailDTO;
import com.example.h2_shop.model.dto.OrdersDTO;
import com.example.h2_shop.service.OrderService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderService orderService;


    /**
     * tạo đơn hàng cho khách hàng
     *
     * @param ordersDTO
     * @return
     * @throws
     * @author dinh van huy
     * @since 5/11/2024
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0 => thay đổi nghiệp vụ thành sao khi admin phê duyệt đơn hàng thì số lượng hàng mới cập nhật
     */
    @PostMapping("/create/Order")
    public ServiceResult<OrdersDTO> createOrder(@RequestBody OrdersDTO ordersDTO){
        ServiceResult<OrdersDTO> serviceResult = this.orderService.createOrder(ordersDTO);
        return serviceResult;
    }

    /**
     * Lấy danh sách đặt hàng của user: không phân trang
     *
     * @param userId
     * @return OrdersDTO
     * @throws
     * @author admin
     * @since 5/11/2024
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0
     */
    @GetMapping("/get-all-order/{userId}")
    public ServiceResult<OrdersDTO> getOrderByUser(@PathVariable Long userId){
        return null;
    }

    /**
     * Lấy danh sách order của khách hàng: phần trang
     *
     * @param
     * @return
     * @throws
     * @author admin
     * @since 5/11/2024
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0
     */
    @PostMapping("/order/search")
    public ServiceResult<Page<OrderRequestDTO>> searchOrderAdmin(@RequestBody OrderRequestDTO orderRequestDTO){
        return this.orderService.searchOrder(orderRequestDTO);
    }

    @GetMapping("/order/detail/{id}")
    public ServiceResult<OrderViewDetailDTO> detailOrderById(@PathVariable Long id){

        return this.orderService.detailOrder(id);
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

    @PostMapping("/update/order")
    public ServiceResult<?> updateOrder(@RequestBody OrderViewDetailDTO orderViewDetailDTO){
        return this.orderService.updateOrder(orderViewDetailDTO);
    }
}

