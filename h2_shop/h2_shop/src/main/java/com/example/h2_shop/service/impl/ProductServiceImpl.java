package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.dto.ProductDTO;
import com.example.h2_shop.service.ProductService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Override
    public ServiceResult<?> createService(List<MultipartFile> listFileAvatar) {

        int countSuccess = 0;
        if(!listFileAvatar.isEmpty()){
            listFileAvatar.forEach(file -> {
                String fileName = file.getOriginalFilename();
                String pathSaveImg = "D:\\IMG_SAVE\\"+fileName;
                File fileIMG = new File(pathSaveImg);
                try {
                    OutputStream opt = new FileOutputStream(fileIMG);
                    opt.write(file.getBytes());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        if(countSuccess==listFileAvatar.size()){
            return new ServiceResult<>("Thanh cong", HttpStatus.OK,"Thanh cong" );
        }else{
            return new ServiceResult<>("That bai", HttpStatus.BAD_REQUEST,"That bai" );
        }
    }
}
