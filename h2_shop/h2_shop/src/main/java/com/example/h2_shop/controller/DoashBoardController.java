package com.example.h2_shop.controller;

import com.example.h2_shop.model.dto.DiagramDetailDTO;
import com.example.h2_shop.service.DoashBoardService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DoashBoardController {
    @Autowired
    DoashBoardService doashBoardService;

    @GetMapping("/doashboard/get-data-for-diagram/{year}")
    public ServiceResult<DiagramDetailDTO> getDataForDiagram(@PathVariable Integer year){
        return this.doashBoardService.getDataForClient(year);
    }


}
