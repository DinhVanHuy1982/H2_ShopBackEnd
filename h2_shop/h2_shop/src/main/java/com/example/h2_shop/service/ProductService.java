package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public ServiceResult<?> createProduct(List<MultipartFile> listFileAvatar, ProductDTO productDTO, List<SizeDTO> sizeDTOList, List<TypeProductDTO> typeProductDTOList);
    public ServiceResult<?> createComment(List<MultipartFile> listFileAvatar);

    public ServiceResult<ProductDTO> getProductById(Long productId);
    public ServiceResult<Page<ProductResponseDTO>> getPageProduct(ProductRequestDTO productRequestDTO);
    public ServiceResult<List<ProductDTO>> getAllProduct();
    public ServiceResult<ProductDetailResponseDTO> detailProductById(Long id);
    public ServiceResult<ProductDetailForClientDTO> detailProductForHome(Long id);
    public ServiceResult<List<ProductBestSellerDTO>> getListBestSeller();
    public ServiceResult<CommentResponseDTO> getDetailComment(Long id);
    public ServiceResult<Page<ProductSearchResponse>> searchProductForUser(ProductRequestDTO productRequestDTO);
}
