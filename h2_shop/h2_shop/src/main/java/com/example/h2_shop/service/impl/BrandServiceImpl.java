package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.Brands;
import com.example.h2_shop.model.dto.BrandsDTO;
import com.example.h2_shop.model.dto.FileDto;
import com.example.h2_shop.model.mapper.BrandMapper;
import com.example.h2_shop.repository.BrandRepository;
import com.example.h2_shop.service.BrandService;
import com.example.h2_shop.service.FileService;
import com.example.h2_shop.service.ServiceResult;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    private final FileService fileService;
    public BrandServiceImpl(BrandRepository brandRepository,  BrandMapper brandMapper, FileService fileService){
        this.brandRepository=brandRepository;
        this.brandMapper = brandMapper;
        this.fileService = fileService;
    }

    @Override
    public ServiceResult<BrandsDTO> createBrand(MultipartFile avatar, BrandsDTO brandsDTO) {

        String errVali = this.validateBrand(brandsDTO);
        ServiceResult<BrandsDTO> serviceResult = new ServiceResult<>();
        if(errVali.isEmpty()){

            Brands brands = this.brandMapper.toEntity(brandsDTO);

            if(avatar!=null){
                ServiceResult<FileDto> fileDtoServiceResult = this.fileService.createFile(avatar);
                brands.setAvatar(fileDtoServiceResult.getData().getFileName());
            }
            brands.setCreateTime(Instant.now());
            brands= this.brandRepository.save(brands);
            brandsDTO = this.brandMapper.toDto(brands);

            serviceResult.setData(brandsDTO);
            serviceResult.setStatus(HttpStatus.OK);
            serviceResult.setMessage("Lưu nhãn hàng thành công");
        }else{
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setMessage(errVali);
        }


        return serviceResult;
    }

    @Override
    public Page<BrandsDTO> getBrandWithPage(BrandsDTO brandsDTO, Pageable pageable) {
        Page<Brands> brandsPage = this.brandRepository.searchBrand(pageable, brandsDTO.getSearchBrand(), brandsDTO.getStatus());

        List<Brands> brandsList = brandsPage.getContent();
        int totalRecord = (int)brandsPage.getTotalElements();

        return new PageImpl<>(this.brandMapper.toDto(brandsList),pageable,totalRecord);
    }

    public String validateBrand(BrandsDTO brandsDTO){
        StringBuilder err = new StringBuilder();

        // code
        if(brandsDTO.getBrandCode()==null){
            err.append(" Mã nhãn hiệu không được để trống ");
        }else if(brandsDTO.getBrandCode().isEmpty()){
            err.append(" Mã nhãn hiệu không được để trống ");
        } else{
            Optional<Brands> brandsOptional = this.brandRepository.findByBrandCode(brandsDTO.getBrandCode());
            if(brandsOptional.isPresent()){
                err.append(" Mã nhãn hiệu đã tồn tại ");
            }
        }

        //name
        if(StringUtils.isEmpty(brandsDTO.getBrandName())){
            err.append(" Tên nhãn hàng không được để trống ");
        }

        //email
        if(StringUtils.isEmpty(brandsDTO.getEmail())){
            err.append(" Email nhãn hàng không được để trống ");
        }else{
            //validate email
        }

        //phone
        if(StringUtils.isEmpty(brandsDTO.getPhoneNumber())){
            err.append(" Số điện thoại nhãn hàng không được để trống ");
        }

        return err.toString();
    }
}
