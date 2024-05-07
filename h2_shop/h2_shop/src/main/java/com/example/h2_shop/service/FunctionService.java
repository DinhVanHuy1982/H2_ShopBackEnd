package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.FunctionsDTO;

import java.util.List;

public interface FunctionService {
    public ServiceResult<List<FunctionsDTO>> getDetailFunction();
}
