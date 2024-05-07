package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.FunctionsDTO;
import com.example.h2_shop.service.FunctionService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FunctionController {


    @Autowired
    FunctionService functionService;

    /**
     * lấy danh sách function và action của nó
     *
     * @param
     * @return
     * @throws
     * @author admin
     * @since 4/24/2024
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0
     */
    @GetMapping("/function/get-function-detail")
    public ServiceResult<List<FunctionsDTO>> getDetailFunction(){
        return this.functionService.getDetailFunction();
    }
}
