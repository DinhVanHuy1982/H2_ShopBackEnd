package com.example.h2_shop.service.impl;

import com.example.h2_shop.commons.ReflectorUtil;
import com.example.h2_shop.model.Product;
import com.example.h2_shop.model.Sale;
import com.example.h2_shop.model.dto.ProductDTO;
import com.example.h2_shop.model.dto.SaleDTO;
import com.example.h2_shop.model.dto.SaleSearchResponseDTO;
import com.example.h2_shop.model.mapper.SaleMapper;
import com.example.h2_shop.repository.SaleRepository;
import com.example.h2_shop.service.ProductService;
import com.example.h2_shop.service.SaleService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    private final SaleMapper saleMapper;
    private final ProductService productService;

    public SaleServiceImpl(SaleRepository saleRepository,ProductService productService,SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper= saleMapper;
        this.productService=productService;
    }

    @Override
    public ServiceResult<SaleDTO> createSale(SaleDTO saleDTO) {
        ServiceResult<SaleDTO> serviceResult = new ServiceResult<>();
        List<Sale> saleOP = this.saleRepository.findByCode(saleDTO.getCode());
        if(saleOP.size()>0){
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setMessage("Mã giảm giá đã tồn tại");
        }else{

            if(saleDTO.getType().equals("1")){
                Sale sale = this.saleMapper.toEntity(saleDTO);
                sale = this.saleRepository.save(sale);
                saleDTO = this.saleMapper.toDto(sale);
                serviceResult.setData(saleDTO);
                serviceResult.setStatus(HttpStatus.OK);
            }else{
                List<Long> lstProductId = saleDTO.getProductIdLst();
                if(lstProductId.size()>0){
                    StringJoiner joiner = new StringJoiner(",");
                    for (Long item : lstProductId) {
                        joiner.add(String.valueOf(item));
                    }
                    List<Sale> lstSaleProductDupplicate = this.saleRepository.findDupplicateSale(saleDTO.getStartTime(),saleDTO.getEndTime(),joiner.toString());
                    if(lstSaleProductDupplicate.size()>0){
                        serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                        serviceResult.setMessage("Tồn tại sản phẩm đang trong thời gian giảm giá khác");
                    }else{
                        for(Long productId: lstProductId){
                            Sale sale = this.saleMapper.toEntity(saleDTO);
                            sale.setCode(saleDTO.getCode());
                            Product product = new Product();
                            product.setId(productId);
                            sale.setProduct(product);
                            this.saleRepository.save(sale);
                        }
                        serviceResult.setMessage("Lưu thành công");
                        serviceResult.setStatus(HttpStatus.OK);
                    }
                }else{
                    serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                    serviceResult.setMessage("Vui lòng chọn sản phẩm để giảm giá");
                }
            }
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Page<SaleSearchResponseDTO>> searchSeal(SaleSearchResponseDTO saleSearchResponseDTO) {

        Pageable pageable = PageRequest.of(saleSearchResponseDTO.getPage()-1,saleSearchResponseDTO.getPageSize());
        Page<Map<String,Object>> map = this.saleRepository.searchSale(pageable,saleSearchResponseDTO.getType(),saleSearchResponseDTO.getKeySearch(), saleSearchResponseDTO.getApplySearch());

        List<SaleSearchResponseDTO> list = map.getContent().stream().map(item -> ReflectorUtil.mapToDTO(item, SaleSearchResponseDTO.class)).collect(Collectors.toList());;

        Page<SaleSearchResponseDTO> page = new PageImpl<>(list,pageable,map.getTotalElements());

        return new ServiceResult<>(page, HttpStatus.OK,"");
    }

    @Override
    public ServiceResult<SaleSearchResponseDTO> getDetailSale(String code) {
        Map<String,Object> map = this.saleRepository.detailSaleByCode(code);
        if(!map.isEmpty()){
            SaleSearchResponseDTO saleSearchResponseDTO = ReflectorUtil.mapToDTO(map, SaleSearchResponseDTO.class);

            List<ProductDTO> lstProduct = this.productService.getAllProduct().getData();

            if(saleSearchResponseDTO.getType().equals("1")){
                saleSearchResponseDTO.setProductDTOList(lstProduct);
            }else{
                String[] mangChuoi = saleSearchResponseDTO.getLstProductStr().split(",");

                // Khởi tạo một danh sách để lưu trữ các số long
                List<Long> danhSachLong = new ArrayList<>();

                // Chuyển đổi từng chuỗi con thành số long và thêm vào danh sách
                for (String str : mangChuoi) {
                    danhSachLong.add(Long.parseLong(str.trim()));
                }

                for(int i =0 ;i<lstProduct.size();i++){
                    if(danhSachLong.contains(lstProduct.get(i).getId())) lstProduct.get(i).setSelected(true);
                    else lstProduct.get(i).setSelected(false);
                }
                saleSearchResponseDTO.setProductDTOList(lstProduct);
            }
            return new ServiceResult<>(saleSearchResponseDTO,HttpStatus.OK,"");
        }
        return new ServiceResult<>(null, HttpStatus.BAD_REQUEST,"Không tồn tại sản phẩm này");
    }

    @Override
    public ServiceResult<SaleDTO> updateSale(SaleDTO saleDTO) {
        ServiceResult<SaleDTO> serviceResult = new ServiceResult<>();
        List<Sale> saleOP = this.saleRepository.findByCode(saleDTO.getCode());
        if(saleOP.size()>0){
            if(saleDTO.getType().equals("1")){
                this.saleRepository.deleteByCode(saleDTO.getCode());
                Sale sale = this.saleMapper.toEntity(saleDTO);
                sale = this.saleRepository.save(sale);
                saleDTO = this.saleMapper.toDto(sale);
                serviceResult.setData(saleDTO);
                serviceResult.setStatus(HttpStatus.OK);
            }else{
                this.saleRepository.deleteByCode(saleDTO.getCode());
                List<Long> lstProductId = saleDTO.getProductIdLst();
                if(lstProductId.size()>0){
                    StringJoiner joiner = new StringJoiner(",");
                    for (Long item : lstProductId) {
                        joiner.add(String.valueOf(item));
                    }
                    List<Sale> lstSaleProductDupplicate = this.saleRepository.findDupplicateSale(saleDTO.getStartTime(),saleDTO.getEndTime(),joiner.toString());
                    if(lstSaleProductDupplicate.size()>0){
                        serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                        serviceResult.setMessage("Tồn tại sản phẩm đang trong thời gian giảm giá khác");
                    }else{
                        for(Long productId: lstProductId){
                            Sale sale = this.saleMapper.toEntity(saleDTO);
                            sale.setCode(saleDTO.getCode());
                            Product product = new Product();
                            product.setId(productId);
                            sale.setProduct(product);
                            this.saleRepository.save(sale);
                        }
                        serviceResult.setMessage("Lưu thành công");
                        serviceResult.setStatus(HttpStatus.OK);
                    }
                }else{
                    serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                    serviceResult.setMessage("Vui lòng chọn sản phẩm để giảm giá");
                }
            }
        }else{
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setMessage("Không tồn tại sản phẩm này");
        }
        return serviceResult;
    }
}
