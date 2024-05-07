package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.Action;
import com.example.h2_shop.model.Function;
import com.example.h2_shop.model.dto.ActionsDTO;
import com.example.h2_shop.model.dto.FunctionsDTO;
import com.example.h2_shop.model.mapper.ActionMapper;
import com.example.h2_shop.model.mapper.FunctionMapper;
import com.example.h2_shop.repository.ActionRepository;
import com.example.h2_shop.repository.FunctionRepository;
import com.example.h2_shop.service.FunctionService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {
    private final FunctionRepository functionRepository;
    private final FunctionMapper functionMapper;
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;

    public FunctionServiceImpl(FunctionRepository functionRepository, FunctionMapper functionMapper, ActionRepository actionRepository, ActionMapper actionMapper) {
        this.functionRepository = functionRepository;
        this.functionMapper = functionMapper;
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
    }

    @Override
    public ServiceResult<List<FunctionsDTO>> getDetailFunction() {
        List<FunctionsDTO> lstFunctionDTO = this.functionMapper.toDto(this.functionRepository.findAll());
        List<ActionsDTO> lstActionDTO = this.actionMapper.toDto(this.actionRepository.findAll());

        lstActionDTO.forEach(item -> item.setSelected(0L));

        lstFunctionDTO.forEach(item -> item.setListActionDTO(lstActionDTO));

        return new ServiceResult<>(lstFunctionDTO, HttpStatus.OK,"Thành công");
    }
}
