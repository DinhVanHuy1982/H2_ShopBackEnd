package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.BrandProduct;
import com.example.h2_shop.model.Brands;
import com.example.h2_shop.model.dto.BrandsDTO;
import com.example.h2_shop.model.dto.FileDto;
import com.example.h2_shop.model.mapper.BrandMapper;
import com.example.h2_shop.repository.BrandProductRepository;
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

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandProductRepository brandProductRepository;
    private final BrandMapper brandMapper;

    private final FileService fileService;
    public BrandServiceImpl(BrandRepository brandRepository,  BrandProductRepository brandProductRepository, BrandMapper brandMapper, FileService fileService){
        this.brandRepository=brandRepository;
        this.brandMapper = brandMapper;
        this.fileService = fileService;
        this.brandProductRepository=brandProductRepository;
    }

    @Override
    public ServiceResult<BrandsDTO> createBrand(MultipartFile avatar, BrandsDTO brandsDTO) {

        String errVali = this.validateBrand(brandsDTO,true);
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
    public ServiceResult<BrandsDTO> updateBrand(MultipartFile avatar, BrandsDTO brandsDTO) throws IOException {

        String errVali = this.validateBrand(brandsDTO,false);
        ServiceResult<BrandsDTO> serviceResult = new ServiceResult<>();
        if(errVali.isEmpty()){

            Optional<Brands> brandsDBOP = this.brandRepository.findById(brandsDTO.getId());
            Brands brands = this.brandMapper.toEntity(brandsDTO);

            if(brandsDBOP.isPresent()){
                Brands brandsDB = brandsDBOP.get();
                brands.setAvatar(brandsDB.getAvatar());
                if(avatar!=null){
                    if(!brandsDB.getAvatar().isEmpty()){
                        this.fileService.deleteFileByName(brandsDB.getAvatar());
                    }
                    ServiceResult<FileDto> fileDtoServiceResult = this.fileService.createFile(avatar);
                    brands.setAvatar(fileDtoServiceResult.getData().getFileName());
                }
                brands.setCreateTime(Instant.now());
                brands= this.brandRepository.save(brands);
                brandsDTO = this.brandMapper.toDto(brands);


                serviceResult.setData(brandsDTO);
                serviceResult.setStatus(HttpStatus.OK);
                serviceResult.setMessage("Cập nhật nhãn hàng thành công");
            }

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

    @Override
    public List<BrandsDTO> getListBrand() {
        List<Brands> lstBrand = this.brandRepository.findByStatus(1L);
        return this.brandMapper.toDto(lstBrand);
    }

    @Override
    public ServiceResult<BrandsDTO> detailBrandById(Long id) {
        Optional<Brands> brandsOP = this.brandRepository.findById(id);
        ServiceResult<BrandsDTO> serviceResult = new ServiceResult<>();
        if(brandsOP.isPresent()){
            BrandsDTO brandsDTO = this.brandMapper.toDto(brandsOP.get());
            serviceResult.setData(brandsDTO);
            serviceResult.setStatus(HttpStatus.OK);
        }else{
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setMessage("Không tồn tại nhãn hàng này");
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<?> deleteBrand(Long id) {
        ServiceResult<?> serviceResult = new ServiceResult<>();
        Optional<Brands> brandsOP=this.brandRepository.findById(id);
        if(brandsOP.isPresent()){
            List<BrandProduct> lstBrandProduct = this.brandProductRepository.findByBrandId(id);
            if(lstBrandProduct.isEmpty()){
                this.brandRepository.delete(brandsOP.get());
                serviceResult.setMessage("Xóa thành công");
                serviceResult.setStatus(HttpStatus.OK);
            }else{
                serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                serviceResult.setMessage("Tồn tại sản phẩm thuộc nhãn hàng, không được xóa");
            }
        }else{
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setMessage("Nhãn hàng không tồn tại");
        }
        return serviceResult;
    }

    public String validateBrand(BrandsDTO brandsDTO, Boolean isCreate){
        StringBuilder err = new StringBuilder();

        // code
        if(brandsDTO.getBrandCode()==null){
            err.append(" Mã nhãn hiệu không được để trống ");
        }else if(brandsDTO.getBrandCode().isEmpty()){
            err.append(" Mã nhãn hiệu không được để trống ");
        } else{
            if(isCreate){
                Optional<Brands> brandsOptional = this.brandRepository.findByBrandCode(brandsDTO.getBrandCode());
                if(brandsOptional.isPresent()){
                    err.append(" Mã nhãn hiệu đã tồn tại ");
                }
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
