package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public ServiceResult<?> createProduct(List<MultipartFile> listFileAvatar, ProductDTO productDTO, List<SizeDTO> sizeDTOList, List<TypeProductDTO> typeProductDTOList);
    public ServiceResult<?> updateProduct(List<MultipartFile> listFileAvatar, ProductDetailResponseDTO productResponseDTO, List<SizeDTO> sizeDTOList, List<TypeProductDTO> typeProductDTOList);
    public ServiceResult<?> createComment(List<MultipartFile> listFileAvatar);

    public ServiceResult<ProductDTO> getProductById(Long productId);
    public ServiceResult<Page<ProductResponseDTO>> getPageProduct(ProductRequestDTO productRequestDTO);
    public ServiceResult<List<ProductDTO>> getAllProduct();
    public ServiceResult<ProductDetailResponseDTO> detailProductById(Long id);
    public ServiceResult<ProductDetailForClientDTO> detailProductForHome(Long id);
    public ServiceResult<List<ProductBestSellerDTO>> getListBestSeller();
    public ServiceResult<CommentResponseDTO> getDetailComment(Long id);
    public ServiceResult<Page<ProductSearchResponse>> searchProductForUser(ProductRequestDTO productRequestDTO);
    public ServiceResult<List<ProductImgDTO>> getListBanner();
    public ServiceResult<OrdersDTO> updatePayStatusOrder(String orderCode);
    public ServiceResult<List<ProductImgDTO>> createBanner(List<MultipartFile> lstBanner,List<Long> lstIdDelete);

    public ServiceResult<ProductDTO> deleteProduct(Long id);

    ServiceResult<SaleForProductDTO> getLstBestSaleForDay();
}
