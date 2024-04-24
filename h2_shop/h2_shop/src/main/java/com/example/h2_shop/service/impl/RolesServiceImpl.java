package com.example.h2_shop.service.impl;

import com.example.h2_shop.commons.ReflectorUtil;
import com.example.h2_shop.model.Function;
import com.example.h2_shop.model.Roles;
import com.example.h2_shop.model.RolesDetails;
import com.example.h2_shop.model.dto.FunctionsDTO;
import com.example.h2_shop.model.dto.RolesDTO;
import com.example.h2_shop.model.dto.RolesDetailsDTO;
import com.example.h2_shop.model.dto.RolesSearchDTO;
import com.example.h2_shop.model.mapper.FunctionMapper;
import com.example.h2_shop.model.mapper.RolesDetailsMapper;
import com.example.h2_shop.model.mapper.RolesMapper;
import com.example.h2_shop.repository.FunctionRepository;
import com.example.h2_shop.repository.RolesDetailsRepository;
import com.example.h2_shop.repository.RolesRepository;
import com.example.h2_shop.repository.customRepo.RoleRepositoryCustom;
import com.example.h2_shop.service.RolesService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RolesServiceImpl implements RolesService {

    @Autowired
    RoleRepositoryCustom roleRepositoryCustom;

    private final RolesRepository rolesRepository;

    private final RolesMapper rolesMapper;
    private final FunctionMapper functionMapper;
    private final RolesDetailsMapper rolesDetailsMapper;
    private final RolesDetailsRepository rolesDetailsRepository;
    private final FunctionRepository functionRepository;
    public RolesServiceImpl(FunctionRepository functionRepository,RolesDetailsRepository rolesDetailsRepository,RolesMapper rolesMapper,RolesRepository rolesRepository,FunctionMapper functionMapper,RolesDetailsMapper rolesDetailsMapper){
        this.rolesMapper=rolesMapper;
        this.rolesRepository=rolesRepository;
        this.functionMapper = functionMapper;
        this.rolesDetailsMapper=rolesDetailsMapper;
        this.rolesDetailsRepository=rolesDetailsRepository;
        this.functionRepository=functionRepository;
    }
    @Override
    public List<RolesDTO> getAllRole() {
        return roleRepositoryCustom.getSearchAllRoleWithNoPage();
    }

    @Override
    public Page<RolesDTO> getRoleWithPage(RolesSearchDTO rolesSearchDTO) {
        Pageable pageable = PageRequest.of(rolesSearchDTO.getPage()-1,10);

        Page<Map<String,Object>> pageRole = this.rolesRepository.findRoleWithPage(pageable, rolesSearchDTO.getRoleSearchName(),rolesSearchDTO.getStatus());

        List<RolesDTO> rolesDTOList = pageRole.getContent().stream().map(role -> ReflectorUtil.mapToDTO(role,RolesDTO.class)).collect(Collectors.toList());

        Page<RolesDTO> pageUpdate = new PageImpl<>(rolesDTOList,pageable,pageRole.getTotalElements());

        return pageUpdate;
    }

    @Override
    public ServiceResult<?> createRole(RolesDTO rolesDTO) {
        Optional<Roles> roles = this.rolesRepository.findByRoleCode(rolesDTO.getRoleCode());
        if(roles.isPresent()){
            return new ServiceResult<>(null, HttpStatus.BAD_REQUEST,"Mã role đã tồn tại");
        }
        Roles rolesSave = this.rolesMapper.toEntity(rolesDTO);
        rolesSave.setCreateTime(Instant.now());
        rolesSave = this.rolesRepository.save(rolesSave);


//        List<Function> functionList = this.functionMapper.toEntity(rolesDTO.getListFunction());
        List<RolesDetailsDTO> rolesDetailsDTOList = rolesDTO.getListRolesDetailsDTO();
        List<RolesDetails> rolesDetailsListSave = new ArrayList<>();
        for (RolesDetailsDTO roles1 : rolesDetailsDTOList) {
            roles1.setRole(rolesSave);
            Optional<Function> Opfunction = this.functionRepository.findById(roles1.getFunctionId());
            if(Opfunction.isPresent()){
                roles1.setFunction(Opfunction.get());
            }

            rolesDetailsListSave.add(this.rolesDetailsMapper.toEntity(roles1));
        }
        rolesDetailsListSave = this.rolesDetailsRepository.saveAll(rolesDetailsListSave);

        return new ServiceResult<>(null, HttpStatus.OK, "Lưu thành công");
    }
}
