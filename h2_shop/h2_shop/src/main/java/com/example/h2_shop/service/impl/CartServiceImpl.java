package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.*;
import com.example.h2_shop.model.dto.CartDTO;
import com.example.h2_shop.model.mapper.CartMapper;
import com.example.h2_shop.repository.*;
import com.example.h2_shop.service.CartService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final TypeProductRepository typeProductRepository;
    private final SizeRepository sizeRepository;
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    CartServiceImpl(UserRepository userRepository,CartMapper cartMapper,CartRepository cartRepository,ProductDetailRepository productDetailRepository,ProductRepository productRepository,SizeRepository sizeRepository,TypeProductRepository typeProductRepository){
        this.userRepository=userRepository;
        this.sizeRepository=sizeRepository;
        this.typeProductRepository=typeProductRepository;
        this.productRepository=productRepository;
        this.productDetailRepository=productDetailRepository;
        this.cartRepository=cartRepository;
        this.cartMapper=cartMapper;
    }
    @Override
    public ServiceResult<CartDTO> createCart(CartDTO cartDTO) {
        ServiceResult<CartDTO> serviceResult= new ServiceResult<>();
        String errCart = this.validationCreateCart(cartDTO);

        if(!errCart.isEmpty()){
            serviceResult.setMessage(errCart);
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            return serviceResult;
        }else{
            Carts carts = new Carts();
            User user = new User();
            user.setId(cartDTO.getUserId());



            Optional<ProductDetail> productDetailOptional = this.productDetailRepository.findByTypeIdSizeIdProductIdWithNull(cartDTO.getTypeProductId(),cartDTO.getSizeProductId(), cartDTO.getProductId());

            carts=this.cartRepository.findByUserIdAndProductDetail(cartDTO.getUserId(),productDetailOptional.get().getId());
            if(carts != null){ // cập nhật
                carts.setQuantity(cartDTO.getQuantity());
                carts.setUpdateTime(Instant.now());
            }else{// thêm mới
                carts = new Carts();
                carts.setUser(user);
                carts.setCreateTime(Instant.now());
                carts.setQuantity(cartDTO.getQuantity());
                carts.setProductDetail(productDetailOptional.get());
                carts = this.cartRepository.save(carts);
            }

            cartDTO = this.cartMapper.toDto(carts);
            serviceResult.setData(cartDTO);
            serviceResult.setStatus(HttpStatus.OK);
            serviceResult.setMessage("Thêm vào giỏ hàng thành công");
            return serviceResult;
        }
    }


    public String validationCreateCart(CartDTO cartDTO){
        String err="";
        if(cartDTO.getQuantity()==null){
            cartDTO.setQuantity(1L);
        }


        if(cartDTO.getUserId()==null){
            err = "Bạn cần phải đăng nhập để thực hiện chức năng này";
        }else{
            Optional<User> userOP = this.userRepository.findById(cartDTO.getUserId());
            if(userOP.isEmpty()){
                err="Người dùng không tồn tại";
            }
        }

        if(cartDTO.getProductId()==null){
            err="Sản phẩm thêm vào giỏ hàng không thể trống";
        } else{
            List<ProductDetail> productDetailsOP = this.productDetailRepository.findAllByProductId(cartDTO.getProductId());
            if(productDetailsOP.size() == 0){
                err = "Khôn tồn tại sản phẩm này";
            }else{
                ProductDetail productDetail = productDetailsOP.get(0);// kiểm tra sản phẩm này có loại và size hay không
                if (productDetail.getTypeProduct()!=null){
                    Optional<TypeProduct> typeProductOP = this.typeProductRepository.findById(cartDTO.getTypeProductId());
                    if(typeProductOP.isEmpty()){
                        err="Không tồn tại loại sản phẩm này";
                    }
                }
                if(productDetail.getSize()!=null){
                    Optional<Size> sizeOP = this.sizeRepository.findById(cartDTO.getSizeProductId());
                    if(sizeOP.isEmpty()){
                        err="Sản phẩm này không có kích cỡ đã chọn";
                    }
                }

                for(int i=0;i<productDetailsOP.size();i++){
                    if(productDetailsOP.get(i).getSize().getId()==cartDTO.getSizeProductId() && productDetailsOP.get(i).getTypeProduct().getId()== cartDTO.getTypeProductId()){
                        productDetail = productDetailsOP.get(i);
                        if(productDetail.getQuantity()<cartDTO.getQuantity()){
                            err="Sản phẩm hiện có không đủ để thêm vào giỏ hàng";
                        }
                        break;
                    }
                }

            }
        }

        return err;
    }
}
