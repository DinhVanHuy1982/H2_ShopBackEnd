package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.*;
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
    ServiceResult<OrderDetailDTO> checkAllowComment(Long userId, Long productId);

    ServiceResult<List<ViewOrderShipAndCompleteDTO>> getListOrderShipAndComplete(Long userId, int type);
}
