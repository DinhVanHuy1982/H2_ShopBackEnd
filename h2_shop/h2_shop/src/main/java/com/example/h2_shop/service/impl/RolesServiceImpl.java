package com.example.h2_shop.service.impl;

import com.example.h2_shop.commons.ReflectorUtil;
import com.example.h2_shop.model.*;
import com.example.h2_shop.model.dto.*;
import com.example.h2_shop.model.mapper.ActionMapper;
import com.example.h2_shop.model.mapper.FunctionMapper;
import com.example.h2_shop.model.mapper.RolesDetailsMapper;
import com.example.h2_shop.model.mapper.RolesMapper;
import com.example.h2_shop.repository.*;
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
import java.util.*;
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
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;
    private final UserRepository userRepository;
    public RolesServiceImpl(ActionMapper actionMapper,UserRepository userRepository,FunctionRepository functionRepository,ActionRepository actionRepository,RolesDetailsRepository rolesDetailsRepository,RolesMapper rolesMapper,RolesRepository rolesRepository,FunctionMapper functionMapper,RolesDetailsMapper rolesDetailsMapper){
        this.rolesMapper=rolesMapper;
        this.rolesRepository=rolesRepository;
        this.functionMapper = functionMapper;
        this.rolesDetailsMapper=rolesDetailsMapper;
        this.rolesDetailsRepository=rolesDetailsRepository;
        this.functionRepository=functionRepository;
        this.actionRepository=actionRepository;
        this.actionMapper=actionMapper;
        this.userRepository=userRepository;
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

    @Override
    public ServiceResult<RoleDetailReturnDTO> getDetailRole(Long id) {
        ServiceResult<RoleDetailReturnDTO> serviceResult = new ServiceResult<>();
        Optional<Roles> rolesOP = this.rolesRepository.findById(id);
        RoleDetailReturnDTO roleDetailReturnDTO = new RoleDetailReturnDTO();
        List<FunctionsDTO> lstFunctionDTOAdd = new ArrayList<>();
        if(rolesOP.isPresent()){
            RolesDTO rolesDTO = this.rolesMapper.toDto(rolesOP.get());
            List<FunctionsDTO> lstFunctionDTO = this.functionMapper.toDto(this.functionRepository.getByRoleId(id));// danh sách các function thuộc role
            List<FunctionsDTO> lstFunctionAllDTO = this.functionMapper.toDto(this.functionRepository.findAll());// danh sách tất cả các function
            List<FunctionsDTO> listFunctionBeetwen = new ArrayList<>();// danh sách các function mà thuộc lstFunctionAllDTO mà không thuộc lstFunctionDTO


            for(int i=0;i<lstFunctionAllDTO.size();i++){
                boolean check = false;
                for(int j =0;j<lstFunctionDTO.size();j++){
                    if(lstFunctionAllDTO.get(i).getFunctionCode().equals(lstFunctionDTO.get(j).getFunctionCode())){
                        check=true;
                    }
                }
                if(!check){
                    listFunctionBeetwen.add(lstFunctionAllDTO.get(i));
                }
            }

            List<RolesDetailsDTO> rolesDetailsDTOList = this.rolesDetailsMapper.toDto(this.rolesDetailsRepository.getListByRoleId(id));

            List<Action> lstActionAll = this.actionRepository.findAll();// lấy tât  cả các action để thêm vào những action còn thieeus thuộc action
            listFunctionBeetwen.forEach(item -> {
                item.setListActionDTO(this.actionMapper.toDto(lstActionAll));
                item.getListActionDTO().forEach(action ->{
                    action.setSelected(0L);
                });
            });
            List<ActionsDTO> actionsDTOList = new ArrayList<>();// danh sách chứa những action không có trong chức năng
            List<Action> actionBeetwen = new ArrayList<>();
            for (RolesDetailsDTO rd : rolesDetailsDTOList){
                for(FunctionsDTO functionsDTO : lstFunctionDTO){
                    if(functionsDTO.getId() == rd.getFunction().getId()){
                        String[] stringArray = rd.getAction().split(",");
                        Long[] longArray = new Long[stringArray.length];
                        for (int i = 0; i < stringArray.length; i++) {
                            longArray[i] = Long.parseLong(stringArray[i]);
                        }
                        List<Long> longList = Arrays.asList(longArray); // Chuyển đổi mảng thành danh sách
                        List<Action> lstAction = this.actionRepository.findAllById(longList); // danh sách các action được thực hiên trong function


                        lstActionAll.forEach(item -> {
                            if(!lstAction.contains(item)){
                                actionBeetwen.add(item);
                            }
                        });

                        actionsDTOList = this.actionMapper.toDto(actionBeetwen);
                        actionsDTOList.forEach(item -> item.setSelected(0L));

                        List<ActionsDTO> lstActionDTO = this.actionMapper.toDto(lstAction);
                        lstActionDTO.forEach(item -> item.setSelected(1L));

                        lstActionDTO.addAll(actionsDTOList);
                        Collections.sort(lstActionDTO, new Comparator<ActionsDTO>() {
                            @Override
                            public int compare(ActionsDTO s1, ActionsDTO s2) {
                                return s1.getId().compareTo(s2.getId());
                            }
                        });

                        functionsDTO.setListActionDTO(lstActionDTO);
                        if(lstAction.size()>0){
                            functionsDTO.setCheckApplyFunction(true);
                        }
                        lstFunctionDTOAdd.add(functionsDTO);
                    }
                }
            }

            lstFunctionDTOAdd.addAll(listFunctionBeetwen);

            Collections.sort(lstFunctionDTOAdd, new Comparator<FunctionsDTO>() {
                @Override
                public int compare(FunctionsDTO s1, FunctionsDTO s2) {
                    return s1.getId().compareTo(s2.getId());
                }
            });

            roleDetailReturnDTO.setId(rolesDTO.getId());
            roleDetailReturnDTO.setRoleName(rolesDTO.getRoleName());
            roleDetailReturnDTO.setRoleCode(rolesDTO.getRoleCode());
            roleDetailReturnDTO.setDescription(rolesDTO.getDescription());
            roleDetailReturnDTO.setStatus(rolesDTO.getStatus());
            roleDetailReturnDTO.setLstFunction(lstFunctionDTOAdd);
            serviceResult.setData(roleDetailReturnDTO);
            serviceResult.setStatus(HttpStatus.OK);
        }else{
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setMessage("Roles không tồn tại");
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<RolesDTO> updateRole(RolesDTO rolesDTO) {
        ServiceResult<RolesDTO> serviceResult = new ServiceResult<>();
        Optional<Roles> rolesOP = this.rolesRepository.findById(rolesDTO.getId());
        if(rolesOP.isPresent()){
            Roles roles = rolesOP.get();
            roles.setStatus(rolesDTO.getStatus());
            roles.setRoleName(rolesDTO.getRoleName());
            roles.setDescription(rolesDTO.getDescription());
            roles.setUpdateTime(Instant.now());

            this.rolesRepository.save(roles);

            List<RolesDetailsDTO> rolesDetailsDTOList = rolesDTO.getListRolesDetailsDTO();

            int countDelete = this.rolesDetailsRepository.deleteRoleDetailByRoleId(rolesDTO.getId());

            List<RolesDetails> rolesDetailsListSave = new ArrayList<>();
            for (RolesDetailsDTO roles1 : rolesDetailsDTOList) {
                roles1.setRole(rolesOP.get());
                Optional<Function> Opfunction = this.functionRepository.findById(roles1.getFunctionId());
                if(Opfunction.isPresent()){
                    roles1.setFunction(Opfunction.get());
                }
                rolesDetailsListSave.add(this.rolesDetailsMapper.toEntity(roles1));
            }
            rolesDetailsListSave = this.rolesDetailsRepository.saveAll(rolesDetailsListSave);
            serviceResult.setStatus(HttpStatus.OK);
            serviceResult.setData(this.rolesMapper.toDto(roles));
        }else{
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setMessage("Role không tồn tại");
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<?> deleteRoleById(Long id) {
        Optional<Roles> rolesOP = this.rolesRepository.findById(id);
        ServiceResult<?> serviceResult = new ServiceResult<>();
        if(rolesOP.isPresent()){
            Roles roles= rolesOP.get();
            Optional<User> userOP = this.userRepository.findUserByRoles(roles.getId());
            if(userOP.isPresent()){
                serviceResult.setMessage("Có tài khoản đang dùng role này");
                serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            }else{
                this.rolesDetailsRepository.deleteRoleDetailByRoleId(roles.getId());
                this.rolesRepository.delete(roles);
                serviceResult.setStatus(HttpStatus.OK);
                serviceResult.setMessage("thành công");
            }
        }else{
            serviceResult.setMessage("Không tồn tại role này");
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
        }
        return serviceResult;
    }
}
