package com.example.h2_shop.service;

import com.example.h2_shop.model.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    public ServiceResult<FileDto> createFile(MultipartFile file);
    public ServiceResult<List<FileDto>> createListFile(List<MultipartFile> file);
    public ServiceResult<?> deleteFileByName(String fileNameToDelete) throws IOException;


}
