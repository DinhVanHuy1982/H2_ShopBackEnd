package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.DiagramDetailDTO;

public interface DoashBoardService {
    public ServiceResult<DiagramDetailDTO> getDataForClient(Integer year);
}
