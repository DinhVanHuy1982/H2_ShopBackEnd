package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.CommentDTO;
import com.example.h2_shop.model.dto.OrderRequestDTO;
import com.example.h2_shop.model.dto.OrderViewDetailDTO;
import com.example.h2_shop.model.dto.OrdersDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrderService {

    public ServiceResult<OrdersDTO> createOrder(OrdersDTO ordersDTOList);
    public ServiceResult<OrdersDTO> getAllOrder(Long userId);
    public ServiceResult<Page<OrderRequestDTO>> searchOrder(OrderRequestDTO orderRequestDTO);
    public ServiceResult<OrderViewDetailDTO> detailOrder(Long id);
    public ServiceResult<CommentDTO> createComment(List<MultipartFile> filesComment, CommentDTO commentDTO);

    public ServiceResult<?> updateOrder(OrderViewDetailDTO orderViewDetailDTO);
}
