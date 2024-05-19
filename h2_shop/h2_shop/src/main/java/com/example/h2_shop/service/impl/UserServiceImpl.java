package com.example.h2_shop.service.impl;

import com.example.h2_shop.Constant;
import com.example.h2_shop.commons.ReflectorUtil;
import com.example.h2_shop.model.*;
import com.example.h2_shop.model.dto.*;
import com.example.h2_shop.model.mapper.*;
import com.example.h2_shop.repository.*;
import com.example.h2_shop.service.FileService;
import com.example.h2_shop.service.MailService;
import com.example.h2_shop.service.ServiceResult;
import com.example.h2_shop.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    FileService fileService;

    @Autowired
    MailService mailService;

    public final RolesRepository rolesRepository;
    public final FunctionRepository functionRepository;
    public final RolesDetailsRepository rolesDetailsRepository;
    public final RolesDetailsMapper rolesDetailsMapper;
    public final ActionRepository actionRepository;
    public final ActionMapper actionMapper;
    public final FunctionMapper functionMapper;
    public final NotifyRepository notifyRepository;
    public final UserMapper userMapper;
    public final NotifyMapper notifyMapper;
    public final RolesMapper rolesMapper;
    public UserServiceImpl(UserMapper userMapper,RolesDetailsRepository rolesDetailsRepository,RolesDetailsMapper rolesDetailsMapper,ActionRepository actionRepository,ActionMapper actionMapper,FunctionMapper functionMapper,FunctionRepository functionRepository,RolesMapper rolesMapper,NotifyRepository notifyRepository,NotifyMapper notifyMapper, RolesRepository rolesRepository){
        this.rolesRepository=rolesRepository;
        this.userMapper=userMapper;
        this.notifyMapper=notifyMapper;
        this.actionMapper=actionMapper;
        this.actionRepository=actionRepository;
        this.notifyRepository=notifyRepository;
        this.rolesMapper=rolesMapper;
        this.functionRepository=functionRepository;
        this.functionMapper = functionMapper;
        this.rolesDetailsRepository=rolesDetailsRepository;
        this.rolesDetailsMapper=rolesDetailsMapper;
    }
    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAllUsers();
    }

    @Override
    public Page<UserResponseDTO> searchUser(SearchFormDTO searchFormDTO) {

        Pageable pageable = PageRequest.of(searchFormDTO.getPage()-1, searchFormDTO.getPageSize());

        Page<Map<String,Object>> map = this.userRepository.searchUser(pageable,searchFormDTO.getKeySearch(), searchFormDTO.getStatus());
        List<UserResponseDTO> lst = map.getContent().stream().map(item -> ReflectorUtil.mapToDTO(item , UserResponseDTO.class)).collect(Collectors.toList());

        Page<UserResponseDTO> pageReturn = new PageImpl<>(lst,pageable,map.getTotalElements());

        return pageReturn;
    }

    @Override
    public UserDto detailUser(Long id) {

        Map<String,Object> map = this.userRepository.detailInforUser(id);
        UserDto userDto = ReflectorUtil.mapToDTO(map,UserDto.class);

        return userDto;
    }

    @Override
    public ServiceResult<?> deleteUser(Long id) {

        List<Orders> ordersOP = this.orderRepository.findByUserId(id);
        if(ordersOP.isEmpty()){
            this.userRepository.deleteById(id);
            return new ServiceResult<>("",HttpStatus.OK,"Xóa thành công");
        }else{
            return new ServiceResult<>("",HttpStatus.BAD_REQUEST,"Tài khoản đã mua hàng, không được xóa");
        }
    }

    @Override
    public ServiceResult<UserDto> createUserNotAvatar(UserDto userDto) {

        ServiceResult<UserDto> serviceResultUser = this.validateUser(userDto);
        if(serviceResultUser.getData()!=null){
            UserDto userDtoVali = serviceResultUser.getData();
            User user = this.userMapper.toEntity(userDtoVali);
            user.setCreateTime(Instant.now());
            user.setStatus(0L);
            user = this.userRepository.save(user);
            UserDto userDtoReturn = this.userMapper.toDto(user);

            ServiceResult<UserDto> userDtoServiceResult = new ServiceResult<>();

//            serviceResultUser.setData(userDtoReturn);
//            serviceResultUser.setMessage("Lưu thành công");
            userDtoServiceResult.setStatus(HttpStatus.OK);
            userDtoServiceResult.setMessage("Lưu thành công");
            userDtoServiceResult.setData(userDtoReturn);
            return userDtoServiceResult;
        }else{
            return serviceResultUser;
        }
    }

    @Override
    public ServiceResult<UserDto> createUser(UserDto userDto, MultipartFile file) {
        ServiceResult<UserDto> serviceResultUser = this.validateUser(userDto); // validate tạo tài khoản
        if(serviceResultUser.getData()!=null){
            UserDto userDtoVali = serviceResultUser.getData();
            User user = this.userMapper.toEntity(userDtoVali);
            user.setFullName(userDto.getFullName());
            if(userDto.getIsCreate()==1) {
                user.setCreateTime(Instant.now());
                String md5Hex = DigestUtils.md5Hex(userDtoVali.getPassword());
                user.setPassword(md5Hex);
            }

            if(user.getStatus()==null){
                user.setStatus(0L);
            }

            FileDto fileDto = new FileDto();
            if(file!=null){ // kiểm tra có upload file ảnh đại diện hay không
                ServiceResult<FileDto> fileDtoServiceResult = this.fileService.createFile(file);
                if(fileDtoServiceResult.getStatus()==HttpStatus.OK){
                    user.setAvatar(fileDtoServiceResult.getData().getFileName());
                    fileDto = fileDtoServiceResult.getData();
                }
            }

            user = this.userRepository.save(user);
            UserDto userDtoReturn = this.userMapper.toDto(user);
            userDtoReturn.setFileDto(fileDto);

            ServiceResult<UserDto> userDtoServiceResult = new ServiceResult<>();
            userDtoServiceResult.setStatus(HttpStatus.OK);
            userDtoServiceResult.setMessage("Lưu thành công");
            userDtoServiceResult.setData(userDtoReturn);
            return userDtoServiceResult;
        }else{
            return serviceResultUser;
        }
    }

    @Override
    public ServiceResult<UserDto> createUserClient(UserDto userDto, MultipartFile file) {
        ServiceResult<UserDto> serviceResultUser = this.validateUserClient(userDto); // validate tạo tài khoản vlient
        if(serviceResultUser.getData()!=null){
            UserDto userDtoVali = serviceResultUser.getData();
            User user = this.userMapper.toEntity(userDtoVali);
            user.setCreateTime(Instant.now());
            user.setStatus(0L);

            FileDto fileDto = new FileDto();
            if(file!=null){ // kiểm tra có upload file ảnh đại diện hay không
                ServiceResult<FileDto> fileDtoServiceResult = this.fileService.createFile(file);
                if(fileDtoServiceResult.getStatus()==HttpStatus.OK){
                    user.setAvatar(fileDtoServiceResult.getData().getFileName());
                    fileDto = fileDtoServiceResult.getData();
                }
            }
            String md5Hex = DigestUtils.md5Hex(userDtoVali.getPassword());
            user.setPassword(md5Hex);
            user = this.userRepository.save(user);
            UserDto userDtoReturn = this.userMapper.toDto(user);
            userDtoReturn.setFileDto(fileDto);

            ServiceResult<UserDto> userDtoServiceResult = new ServiceResult<>();
            userDtoServiceResult.setStatus(HttpStatus.OK);
            userDtoServiceResult.setMessage("Lưu thành công");
            userDtoServiceResult.setData(userDtoReturn);
            return userDtoServiceResult;
        }else{
            return serviceResultUser;
        }
    }

    @Override
    public ServiceResult<NotifyDTO> forgotPassword(UserDto userDto) throws MessagingException {

        ServiceResult<NotifyDTO> serviceResult = new ServiceResult<>();
        if(userDto.getUsername()==null){
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setMessage("Tên tài khoản không được để trống");
            return serviceResult;
        }else{
            Optional<User> userOp=this.userRepository.findByUsername(userDto.getUsername());
            if(userOp.isEmpty()){
                serviceResult.setMessage("Tài khoản không tồn tại");
                serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                return serviceResult;
            }else{
                User user = userOp.get();
                if(user.getResetCount()==null){ // bắt cho lần đầu tiên lấy mật  khẩu của tài khoản
                    user.setResetCount(1);
                    user.setResetDate(Instant.now());
                }else{
                    Instant now = Instant.now();
                    Instant dateReset = user.getResetDate();

                    LocalDate localDate1 = now.atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate localDate2 = dateReset.atZone(ZoneId.systemDefault()).toLocalDate();

                    if(localDate1.equals(localDate2)){
                        if(user.getResetCount()>=3){
                            serviceResult.setMessage("Đã quá số lần lấy lại mật khẩu trong ngày!");
                            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                            return serviceResult;
                        }else{
                            user.setResetDate(Instant.now());
                            int count = user.getResetCount();
                            user.setResetCount(++count);
                        }
                    }else{
                        user.setResetCount(1);
                        user.setResetDate(Instant.now());
                    }
                }

                Random random = new Random();

                // Tạo code reset mật khẩu từ 6 chữ số ngẫu nhiên
                int randomNumber = random.nextInt(900000) + 100000;
                user.setCodeReset(Long.parseLong(randomNumber+""));
                this.userRepository.save(user);

                String content = Constant.getContentForgotPassword(user.getUsername(),randomNumber+"");
                String titleMail = "[Thông báo] Khôi phục mật khẩu";
                Notify notify = new Notify();

                notify.setUser(user);
                notify.setContent(content);
                notify.setSendDate(Instant.now());
                notify.setSendType("RESET_PASSWORD");
                notify.setTitle(titleMail);
                notify = this.notifyRepository.save(notify);
                NotifyDTO notifyDTO = this.notifyMapper.toDto(notify);

                this.mailService.sendMail(user.getEmail(),titleMail,content);

                serviceResult.setStatus(HttpStatus.OK);
                serviceResult.setData(notifyDTO);
                serviceResult.setMessage("Vui lòng kiểm tra thư đăng ký");
                return serviceResult;
            }
        }
    }

    @Override
    public ServiceResult<?> confirmPassWord(UserDto userDto) {
        ServiceResult<String> serviceResult = new ServiceResult<>();
        if(userDto.getUsername()==null){
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setMessage("Tên tài khoản không được để trống");

        }else{
            if(userDto.getCodeReset()==null){
                serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                serviceResult.setMessage("Mã xác nhận không được để trống");
            }else{
                Optional<User> userOP = this.userRepository.findByUsername(userDto.getUsername());
                if(userOP.isEmpty()){
                    serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                    serviceResult.setMessage("Tài khoản không tồn tại");
                }else{
                    User user = userOP.get();
                    Instant now = Instant.now();
                    Instant timeReset = user.getResetDate();

                    LocalDate localDate1 = now.atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate localDate2 = timeReset.atZone(ZoneId.systemDefault()).toLocalDate();
                    if(localDate1.equals(localDate2)){
                        if(userDto.getCodeReset().equals(user.getCodeReset())){
                            // Tính thời gian chênh lệch giữa hai Instant
                            Duration duration = Duration.between(timeReset, now);

                            // Kiểm tra xem thời gian chênh lệch có lớn hơn 2 phút hay không
                            if (duration.abs().toMinutes() > 2) {//Hai thời điểm cách nhau quá 2 phút.
                                serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                                serviceResult.setMessage("Đã quá thời gian lấy lại mật khẩu");

                            } else {//Hai thời điểm không cách nhau quá 2 phút.
                                String newPass = generatePassword();
                                String md5Hex = DigestUtils.md5Hex(newPass);
                                user.setPassword(md5Hex);
                                this.userRepository.save(user);
                                try{
                                    this.mailService.sendMail(user.getEmail(),"Mật khẩu mới của quý khách là: ",newPass);
                                }catch (Exception e){
                                    System.out.println(e);
                                }
                                serviceResult.setMessage("Lấy lại mật khẩu thành công, vui lòng kiểm tra thư");
                                serviceResult.setStatus(HttpStatus.OK);
                                serviceResult.setData(null);

                            }
                        }else{
                            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                            serviceResult.setMessage("Yêu cầu nhập đúng mã OTP");
                        }
                    }else{
                        serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                        serviceResult.setMessage("Cần phải tạo yêu cầu lấy mã reset");

                    }
                }
            }
        }

        return serviceResult;
    }

    @Override
    public ServiceResult<UserDto> loginUser(UserDto userDto) {

        ServiceResult<UserDto> serviceResult = new ServiceResult<>();
        if(StringUtils.isEmpty(userDto.getUsername())){
            serviceResult.setMessage("Không được để trống tên đăng nhập");
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
        } else if (StringUtils.isEmpty(userDto.getPassword())) {
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setMessage("Mật khẩu không được để trống");
        }else{
            String md5Hex = DigestUtils.md5Hex(userDto.getPassword());
            Optional<User> userOP = this.userRepository.findByUsernameAndPassword(userDto.getUsername(),md5Hex);
            if (userOP.isPresent()){
                userDto = this.userMapper.toDto(userOP.get());
                if(userDto.getStatus()!=1){
                    serviceResult.setMessage("Tài khoản không hoạt động");
                }else if(userDto.getRoles()!=null){
                    if(userDto.getRoles().getStatus()!=1){
                        serviceResult.setMessage("Tài khoản không có quyền truy cập hệ thống");
                    }else{
                        RolesDTO rolesDTO = this.rolesMapper.toDto(userDto.getRoles());
                        List<RolesDetails> lstRoleDetail = this.rolesDetailsRepository.getListByRoleId(rolesDTO.getId());
                        List<Function> listFunction = this.functionRepository.getByRoleId(rolesDTO.getId());
                        List<FunctionsDTO> lstFunctionDto = this.functionMapper.toDto(listFunction);
                        List<String> lstFunctionCode = new ArrayList<>();
                        for (int i =0 ;i<lstRoleDetail.size();i++){
                            List<Action> lstAction = this.actionRepository.getActionByListId(lstRoleDetail.get(i).getAction());
                            List<ActionsDTO> lstActionDto = this.actionMapper.toDto(lstAction);
                            for(int j=0;j<lstActionDto.size();j++){
                                if(lstActionDto.get(j).getCode().equals("create")){
                                    lstFunctionDto.get(i).setCreate(true);
                                }
                                if(lstActionDto.get(j).getCode().equals("update")){
                                    lstFunctionDto.get(i).setUpdate(true);
                                }
                                if(lstActionDto.get(j).getCode().equals("delete")){
                                    lstFunctionDto.get(i).setDelete(true);
                                }
                                if(lstActionDto.get(j).getCode().equals("search")){
                                    lstFunctionDto.get(i).setSearch(true);
                                }
                                if(lstActionDto.get(j).getCode().equals("export")){
                                    lstFunctionDto.get(i).setExport(true);
                                }
                                if(lstActionDto.get(j).getCode().equals("import")){
                                    lstFunctionDto.get(i).setImport(true);
                                }
                            }
                            lstFunctionDto.get(i).setListActionDTO(lstActionDto);
                            lstFunctionCode.add(lstFunctionDto.get(i).getFunctionCode());
                        }
                        rolesDTO.setListFunction(lstFunctionDto);
                        userDto.setRolesDTO(rolesDTO);
                        userDto.setLstFunctionCode(lstFunctionCode);
                        serviceResult.setMessage("Đăng nhập thành công");
                        serviceResult.setStatus(HttpStatus.OK);
                        serviceResult.setData(userDto);
                    }
                }else{
                    userDto.setRoles(null);
                    serviceResult.setMessage("Đăng nhập thành công");
                    serviceResult.setStatus(HttpStatus.OK);
                    serviceResult.setData(userDto);
                }
            }else{
                serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                serviceResult.setMessage("Tên tài khoản hoặc mật khẩu không chính xác");
            }
        }
        return serviceResult;
    }

    public String generatePassword() {
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numericChars = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[{]}|;:,<.>/?";

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // Thêm ít nhất 1 ký tự từ mỗi loại vào mật khẩu
        password.append(uppercaseChars.charAt(random.nextInt(uppercaseChars.length())));
        password.append(lowercaseChars.charAt(random.nextInt(lowercaseChars.length())));
        password.append(numericChars.charAt(random.nextInt(numericChars.length())));

        // Thêm 1 ký tự đặc biệt vào mật khẩu
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        // Tạo số ký tự cần thêm vào mật khẩu (tổng số ký tự - số ký tự đã thêm)
        int remainingChars = 6 - password.length();

        // Thêm các ký tự ngẫu nhiên từ các loại ký tự khác vào mật khẩu
        for (int i = 0; i < remainingChars; i++) {
            String allChars = uppercaseChars + lowercaseChars + numericChars;
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Trộn ngẫu nhiên các ký tự trong mật khẩu
        String shuffledPassword = shuffleString(password.toString());

        return shuffledPassword;
    }

    public String shuffleString(String string) {
        char[] characters = string.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = (int) (Math.random() * characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    public ServiceResult<UserDto> validateUserClient(UserDto userDto){

        ServiceResult<UserDto> userServiceResult = new ServiceResult<>();
        StringBuilder messError = new StringBuilder();

        //UserName
        if(StringUtils.isEmpty( userDto.getUsername())){
            messError.append("Username không được để trống");
            userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
        }else{
            Optional<User> userCheckUserName = this.userRepository.findByUsername(userDto.getUsername());
            if(userCheckUserName.isPresent()){
                messError.append("Username đã tồn tại");
                userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            }
        }

        //email
        if(StringUtils.isEmpty(userDto.getEmail())){
            userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            messError.append(" Email không được để trống ");
        }else{
            // validate định dạng email
        }

        // roleid
//        if(userDto.getRoleId()==null){
//            userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
//            messError.append(" Không được để trống trường role ");
//        }else{
//            Optional<Roles> roles = this.rolesRepository.findById(userDto.getRoleId());
//            if(roles.isEmpty()){
//                userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
//                messError.append(" Mã role không tồn tại ");
//            }else{
//                userDto.setRoles(roles.get());
//            }
//        }

        //password
        if(StringUtils.isEmpty(userDto.getPassword())){
            userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            messError.append(" Mật khẩu Không được để trống");
        }else{
            if(userDto.getPassword().length()<=6){
                userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
                messError.append(" Mật khẩu phải nhiều hơn 6 kí tự ");
            }
        }

        if(userServiceResult.getStatus()!= HttpStatus.BAD_REQUEST){
            userServiceResult.setStatus(HttpStatus.OK);
            userServiceResult.setData(userDto);
        }else{
            userServiceResult.setData(null);
            userServiceResult.setMessage(messError.toString());
        }

        return userServiceResult;
    }

    public ServiceResult<UserDto> validateUser(UserDto userDto){

        ServiceResult<UserDto> userServiceResult = new ServiceResult<>();
        StringBuilder messError = new StringBuilder();

        //UserName
        if(userDto.getUsername().isEmpty()){
            messError.append("Username không được để trống");
            userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
        }else{
            if(userDto.getIsCreate()==1){
                Optional<User> userCheckUserName = this.userRepository.findByUsername(userDto.getUsername());
                if(userCheckUserName.isPresent()){
                    messError.append("Username đã tồn tại");
                    userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
                }
            }else{
                Optional<User> userCheckUserName = this.userRepository.findByUsername(userDto.getUsername());

                if(!userCheckUserName.isPresent()){
                    messError.append("Username không tồn tồn tại");
                    userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
                }else{
                    userDto.setPassword(userCheckUserName.get().getPassword());
                }
            }
        }

        //email
        if(userDto.getEmail().isEmpty()){
            userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            messError.append(" Email không được để trống ");
        }else{
            // validate định dạng email
        }

        // roleid
        if(userDto.getRoleId()==null){
            userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            messError.append(" Không được để trống trường role ");
        }else{
            Optional<Roles> roles = this.rolesRepository.findById(userDto.getRoleId());
            if(roles.isEmpty()){
                userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
                messError.append(" Mã role không tồn tại ");
            }else{
                userDto.setRoles(roles.get());
            }
        }

        //password
        if(userDto.getIsCreate()==1){
            if(StringUtils.isBlank(userDto.getPassword())){
                userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
                messError.append(" Mật khẩu Không được để trống");
            }else{
                if(userDto.getPassword().length()<=6){
                    userServiceResult.setStatus(HttpStatus.BAD_REQUEST);
                    messError.append(" Mật khẩu phải nhiều hơn 6 kí tự ");
                }
            }
        }

        if(userServiceResult.getStatus()!= HttpStatus.BAD_REQUEST){
            userServiceResult.setStatus(HttpStatus.OK);
            userServiceResult.setData(userDto);
        }else{
            userServiceResult.setData(null);
            userServiceResult.setMessage(messError.toString());
        }

        return userServiceResult;
    }
}
