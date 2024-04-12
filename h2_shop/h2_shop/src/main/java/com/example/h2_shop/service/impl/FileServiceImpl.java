package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.dto.FileDto;
import com.example.h2_shop.service.FileService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    public ServiceResult<?> createComment(List<MultipartFile> listFileAvatar) {
        return null;
    }

    @Override
    public ServiceResult<FileDto> createFile(MultipartFile file) {
        LocalDateTime currentTime = LocalDateTime.now();
        String id = currentTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String fileName = file.getOriginalFilename();
        String pathSaveImg = "D:\\IMG_SAVE\\"+id+"_"+fileName;
        File fileIMG = new File(pathSaveImg);
        try {
            OutputStream opt = new FileOutputStream(fileIMG);
            opt.write(file.getBytes());
            FileDto fileDto = new FileDto(id, id+"_"+fileName,file.getSize());
            return new ServiceResult<>(fileDto, HttpStatus.OK,"Thành công ");
        } catch (FileNotFoundException e) {
            return new ServiceResult<>(null, HttpStatus.BAD_REQUEST,"File không tồn tại" );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ServiceResult<List<FileDto>> createListFile(List<MultipartFile> listFileAvatar) {
        int countSuccess = 0;
        List<FileDto> fileDtoList = new ArrayList<>();
        if(!listFileAvatar.isEmpty()){
            for(int i = 0; i<listFileAvatar.size();i++){
                LocalDateTime currentTime = LocalDateTime.now();
                String id = currentTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

                String fileName = listFileAvatar.get(i).getOriginalFilename();
                String pathSaveImg = "D:\\IMG_SAVE\\"+id+"_"+fileName;
                File fileIMG = new File(pathSaveImg);
                try {
                    FileDto fileDto = new FileDto();
                    long fileSize = listFileAvatar.get(i).getSize();
                    fileDto.setFileSize(fileSize);
                    fileDto.setFileId(id);
                    fileDto.setFileName(id+fileName);

                    OutputStream opt = new FileOutputStream(fileIMG);
                    opt.write(listFileAvatar.get(i).getBytes());
                    fileDtoList.add(fileDto);
                    countSuccess++;
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if(countSuccess!=0){
            return new ServiceResult<>(fileDtoList, HttpStatus.OK,"Thành công "+ countSuccess+"/"+listFileAvatar.size());
        }else{
            return new ServiceResult<>(null, HttpStatus.BAD_REQUEST,"Thhất bại" );
        }
    }

    @Override
    public ServiceResult<?> deleteFileByName(String fileNameToDelete) throws IOException {
        // Tạo đường dẫn đến tệp cần xóa
        Path filePath = Paths.get("D:\\IMG_SAVE\\", fileNameToDelete);

        ServiceResult<?> serviceResult = new ServiceResult<>();
        // Kiểm tra xem tệp tồn tại không
        if (Files.exists(filePath)) {
            // Thực hiện xóa tệp
            Files.delete(filePath);
            serviceResult.setMessage("Xóa file thành công");
            serviceResult.setStatus(HttpStatus.OK);
            System.out.println("Tệp " + fileNameToDelete + " đã được xóa thành công.");
        } else {
            serviceResult.setMessage("Xóa file không thành công");
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            System.out.println("Tệp " + fileNameToDelete + " không tồn tại.");
        }
        return serviceResult;
    }
}
