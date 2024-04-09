package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.Product;
import com.example.h2_shop.model.Size;
import com.example.h2_shop.model.dto.ProductDTO;
import com.example.h2_shop.model.mapper.ProductMapper;
import com.example.h2_shop.model.mapper.SizeMapper;
import com.example.h2_shop.service.ProductService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    SizeMapper sizeMapper;



    @Override
    public ServiceResult<?> createProduct(List<MultipartFile> listFileAvatar,ProductDTO productDTO) {

        int countSuccess = 0;
        if(!listFileAvatar.isEmpty()){
            for(int i = 0; i<listFileAvatar.size();i++){
                LocalDateTime currentTime = LocalDateTime.now();
                String id = currentTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

                String fileName = listFileAvatar.get(i).getOriginalFilename();
                String pathSaveImg = "D:\\IMG_SAVE\\"+id+"_"+fileName;
                File fileIMG = new File(pathSaveImg);
                try {
                    OutputStream opt = new FileOutputStream(fileIMG);
                    opt.write(listFileAvatar.get(i).getBytes());
                    Product product = this.productMapper.toEntity(productDTO);
                    Instant createTime = Instant.now();
                    product.setCreateTime(createTime);

                    List<Size> listSize = this.sizeMapper.toEntity(productDTO.getSizeDTOList());


                    countSuccess++;
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if(countSuccess!=0){
            return new ServiceResult<>("Thanh cong", HttpStatus.OK,"Thành công "+ countSuccess+"/"+listFileAvatar.size());
        }else{
            return new ServiceResult<>("That bai", HttpStatus.BAD_REQUEST,"Thhất bại" );
        }
    }

    @Override
    public ServiceResult<?> createComment(List<MultipartFile> listFileAvatar) {
        int countSuccess = 0;
        if(!listFileAvatar.isEmpty()){
            for(int i = 0; i<listFileAvatar.size();i++){
                LocalDateTime currentTime = LocalDateTime.now();
                String id = currentTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

                String fileName = listFileAvatar.get(i).getOriginalFilename();
                String pathSaveImg = "D:\\IMG_SAVE\\"+id+"_"+fileName;
                File fileIMG = new File(pathSaveImg);
                try {
                    OutputStream opt = new FileOutputStream(fileIMG);
                    opt.write(listFileAvatar.get(i).getBytes());
                    countSuccess++;
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if(countSuccess!=0){
            return new ServiceResult<>("Thanh cong", HttpStatus.OK,"Thành công "+ countSuccess+"/"+listFileAvatar.size());
        }else{
            return new ServiceResult<>("That bai", HttpStatus.BAD_REQUEST,"Thhất bại" );
        }
    }
}
