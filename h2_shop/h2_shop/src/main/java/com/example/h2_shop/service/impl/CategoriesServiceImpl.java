package com.example.h2_shop.service.impl;

import com.example.h2_shop.model.Categories;
import com.example.h2_shop.model.Product;
import com.example.h2_shop.model.dto.CategoriesDTO;
import com.example.h2_shop.model.mapper.CategoriesMapper;
import com.example.h2_shop.repository.CategoriesRepository;
import com.example.h2_shop.repository.CategoriesRepositoryCustome;
import com.example.h2_shop.repository.ProductRepository;
import com.example.h2_shop.service.CategoriesService;
import com.example.h2_shop.service.ServiceResult;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final CategoriesRepositoryCustome categoriesRepositoryCustome;
    private final ProductRepository productRepository;
    private final CategoriesMapper categoriesMapper;

    public CategoriesServiceImpl(CategoriesRepository categoriesRepository,
                                 ProductRepository productRepository,
                                 CategoriesRepositoryCustome categoriesRepositoryCustome,
                                 CategoriesMapper categoriesMapper){
        this.categoriesMapper=categoriesMapper;
        this.categoriesRepositoryCustome=categoriesRepositoryCustome;
        this.categoriesRepository=categoriesRepository;
        this.productRepository=productRepository;
    }




    @Override
    public ServiceResult<List<CategoriesDTO>> getTreeCategories(CategoriesDTO categoriesDTO) {
        List<CategoriesDTO> lst = this.categoriesRepositoryCustome.getAllCategories(categoriesDTO);
        List<CategoriesDTO> tree = this.makeTree(lst);


        if(categoriesDTO.getId()!=null){
            removeCategoryAndDescendants(tree,categoriesDTO.getId());
            List<CategoriesDTO> returnLst = new ArrayList<>();
            tree.forEach(item -> returnLst.addAll(flatten(item)));
            return new ServiceResult<>(returnLst,HttpStatus.OK,"Thành công");
        }

        return new ServiceResult<>(tree,HttpStatus.OK,"Thành công");
    }

    public void removeCategoryAndDescendants(List<CategoriesDTO> categories, Long targetId) {
        Iterator<CategoriesDTO> iterator = categories.iterator();
        while (iterator.hasNext()) {
            CategoriesDTO category = iterator.next();
            if (category.getId() == targetId) {
                iterator.remove();
                continue;
            }
            if(category.getChildren()!=null){
                removeCategoryAndDescendants(category.getChildren(), targetId);
            }
        }
    }

    public List<CategoriesDTO> flatten(CategoriesDTO root) {
        List<CategoriesDTO> flattenedList = new ArrayList<>();
        if (root == null) {
            return flattenedList;
        }
        flattenedList.add(root); // Thêm nút hiện tại vào danh sách

        if (root.getChildren() != null) {
            for (CategoriesDTO child : root.getChildren()) {
                flattenedList.addAll(flatten(child)); // Thêm tất cả các nút con vào danh sách
            }
        }

        return flattenedList;
    }

    @Override
    public ServiceResult<List<CategoriesDTO>> getNoTreeCategories() {
        List<CategoriesDTO> categoriesDTOList = this.categoriesMapper.toDto(this.categoriesRepository.findAllByOrderByCategoriCode());
        return new ServiceResult<>(categoriesDTOList,HttpStatus.OK,"Thành công");
    }

    @Override
    public ServiceResult<CategoriesDTO> createCategories(CategoriesDTO categoriesDTO) {
        String errVali = this.validateCategories(categoriesDTO,true);
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

    @Override
    public ServiceResult<CategoriesDTO> updateCategories(CategoriesDTO categoriesDTO) {
        String err = this.validateCategories(categoriesDTO,false);
        ServiceResult<CategoriesDTO> serviceResult = new ServiceResult<>();
        if(categoriesDTO.getId()!=null){
            Optional<Categories> categoriesOP = categoriesRepository.findById(categoriesDTO.getId());
            if(categoriesOP.isPresent()){
                Categories categories = categoriesOP.get();
                categories = this.categoriesMapper.toEntity(categoriesDTO);
                categories = this.categoriesRepository.save(categories);
                serviceResult.setData(this.categoriesMapper.toDto(categories));
                serviceResult.setMessage("Cập nhật thành công");
                serviceResult.setStatus(HttpStatus.OK);
            }else{
                serviceResult.setData(null);
                serviceResult.setMessage("Không tồn tại danh mục này");
                serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            }
        }else{
            serviceResult.setData(null);
            serviceResult.setMessage("Không tồn tại danh mục này");
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<CategoriesDTO> getById(Long id) {
        Optional<Categories> categoriesOP = this.categoriesRepository.findById(id);
        if(categoriesOP.isPresent()){
            return new ServiceResult<>(this.categoriesMapper.toDto(categoriesOP.get()),HttpStatus.OK,"Thành công");
        }else{
            return new ServiceResult<>(null,HttpStatus.OK,"Không tồn tại danh mục này");

        }
    }

    @Override
    public ServiceResult<?> deleteCategoriesById(Long id) {
        ServiceResult<?> serviceResult = new ServiceResult<>();
        Optional<Categories> categoriesOP = this.categoriesRepository.findById(id);
        if(categoriesOP.isPresent()){
            List< Product> productList = this.productRepository.getProductByCategoriId(id);
            if(productList.isEmpty()){
                List<Categories> categoriesChild = this.categoriesRepository.findByParentId(id);
                categoriesChild.forEach(item -> item.setParentId(null));
                this.categoriesRepository.saveAll(categoriesChild);
                this.categoriesRepository.delete(categoriesOP.get());
                serviceResult.setStatus(HttpStatus.OK);
                serviceResult.setMessage("Xóa thành công");
            }else {
                serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                serviceResult.setMessage("Tồn tại sản phẩm thuộc danh mục này");
            }
        }else{
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setMessage("Danh mục này không tồn tại");
        }

        return serviceResult;
    }

    @Override
    public ServiceResult<List<CategoriesDTO>> getTreeCategoriesStatus() {
        List<CategoriesDTO> lst = this.categoriesRepositoryCustome.getAllCategoriesActive();
        List<CategoriesDTO> tree = this.makeTree(lst);
        return new ServiceResult<>(tree,HttpStatus.OK,"Thành công");
    }


    public String validateCategories(CategoriesDTO categoriesDTO,boolean isCreate){
        StringBuilder err = new StringBuilder();

        //code
        if(StringUtils.isEmpty(categoriesDTO.getCategoriCode())){
            err.append("Mã danh mục không được để trống\n");
        }else{
            Optional<Categories> OpCategories = this.categoriesRepository.findByCategoriCode(categoriesDTO.getCategoriCode());
            if(isCreate){
                if(OpCategories.isPresent()){
                    err.append(" Mã danh mục đã tồn tại \n");
                }
            }else{
                if (OpCategories.isEmpty()){
                    err.append("Danh mục không tồn tại\n");
                }
            }
        }

        //name
        if(StringUtils.isEmpty(categoriesDTO.getCategoriName())){
            err.append(" Tên danh mục không được để trống \n");
        }

        //parent id
        if(categoriesDTO.getParentId()!=null){
            Optional<Categories> categories = this.categoriesRepository.findById(categoriesDTO.getParentId());
            if(categories.isEmpty()){
                err.append(" Danh mục cha không tồn tại \n");
            }else{
                List<Product> lstProductOfCategories = this.productRepository.getProductByCategoriId(categories.get().getId());
                if (!lstProductOfCategories.isEmpty()){
                    err.append("Danh mục cha có sản phẩm thuộc. Vui lòng chọn danh mục cha khác\n");
                }
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
