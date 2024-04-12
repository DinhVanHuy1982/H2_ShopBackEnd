package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.Categories;
import com.example.h2_shop.model.dto.CategoriesDTO;
import com.example.h2_shop.model.mapper.CategoriesMapper;
import com.example.h2_shop.repository.CategoriesRepository;
import com.example.h2_shop.repository.CategoriesRepositoryCustome;
import com.example.h2_shop.service.CategoriesService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final CategoriesRepositoryCustome categoriesRepositoryCustome;
    private final CategoriesMapper categoriesMapper;

    public CategoriesServiceImpl(CategoriesRepository categoriesRepository,CategoriesRepositoryCustome categoriesRepositoryCustome,CategoriesMapper categoriesMapper){
        this.categoriesMapper=categoriesMapper;
        this.categoriesRepositoryCustome=categoriesRepositoryCustome;
        this.categoriesRepository=categoriesRepository;
    }




    @Override
    public ServiceResult<List<CategoriesDTO>> getTreeCategories() {

        List<CategoriesDTO> categoriesDTOList = this.makeTree(this.categoriesRepositoryCustome.getAllCategoriesActive());
        return new ServiceResult<>(categoriesDTOList,HttpStatus.OK,"Thành công");
    }

    @Override
    public ServiceResult<CategoriesDTO> createCategories(CategoriesDTO categoriesDTO) {
        String errVali = this.validateCategories(categoriesDTO);
        ServiceResult<CategoriesDTO> serviceResult = new ServiceResult<>();
        if(errVali.isEmpty()){

            Categories categories =  this.categoriesMapper.toEntity(categoriesDTO);
            categories.setCreateTime(Instant.now());
            categories = this.categoriesRepository.save(categories);

            categoriesDTO = this.categoriesMapper.toDto(categories);

            serviceResult.setData(categoriesDTO);
            serviceResult.setMessage("Lưu thành công");
            serviceResult.setStatus(HttpStatus.OK);
        }else{
            serviceResult.setData(null);
            serviceResult.setMessage(errVali);
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
        }
        return serviceResult;
    }


    public String validateCategories(CategoriesDTO categoriesDTO){
        StringBuilder err = new StringBuilder();

        //code
        if(categoriesDTO.getCategoriCode().isEmpty()){
            err.append("Mã danh mục không được để trống");
        }else{
            Optional<Categories> OpCategories = this.categoriesRepository.findByCategoriCode(categoriesDTO.getCategoriCode());
            if(OpCategories.isPresent()){
                err.append(" Mã danh mục đã tồn tại ");
            }
        }

        //name
        if(categoriesDTO.getCategoriName().isEmpty()){
            err.append(" Tên danh mục không được để trống ");
        }

        //parent id
        if(categoriesDTO.getParentId()!=null){
            Optional<Categories> categories = this.categoriesRepository.findById(categoriesDTO.getParentId());
            if(categories.isEmpty()){
                err.append(" Danh mục cha không tồn tại ");
            }
        }
        return err.toString();
    }


    private List<CategoriesDTO> makeTree(List<CategoriesDTO> CategoriesDTOS) {
        List<CategoriesDTO> libCategoryDTOParentNullList = new ArrayList<>();

        for (CategoriesDTO libCategoryDTOSearch : CategoriesDTOS) {
            if (libCategoryDTOSearch.getParentId() == null) {
                libCategoryDTOParentNullList.add(libCategoryDTOSearch);
            }

            for (CategoriesDTO libCategoryDTO1 : CategoriesDTOS) {
                if (Objects.equals(libCategoryDTO1.getParentId(), libCategoryDTOSearch.getId())) {
                    if (libCategoryDTOSearch.getChildren().size() == 0) {
                        List<CategoriesDTO> childrenList = new ArrayList<>();
                        childrenList.add(libCategoryDTO1);
                        libCategoryDTOSearch.setChildren(childrenList);
                    } else {
                        libCategoryDTOSearch.getChildren().add(libCategoryDTO1);
                    }
                }
            }
        }

        return libCategoryDTOParentNullList;
    }
}
