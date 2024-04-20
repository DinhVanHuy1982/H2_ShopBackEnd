package com.example.h2_shop.service.impl;

import com.example.h2_shop.Constant;
import com.example.h2_shop.model.Notify;
import com.example.h2_shop.model.Roles;
import com.example.h2_shop.model.User;
import com.example.h2_shop.model.dto.FileDto;
import com.example.h2_shop.model.dto.NotifyDTO;
import com.example.h2_shop.model.dto.UserDto;
import com.example.h2_shop.model.mapper.NotifyMapper;
import com.example.h2_shop.model.mapper.UserMapper;
import com.example.h2_shop.repository.NotifyRepository;
import com.example.h2_shop.repository.RolesRepository;
import com.example.h2_shop.repository.UserRepository;
import com.example.h2_shop.service.FileService;
import com.example.h2_shop.service.MailService;
import com.example.h2_shop.service.ServiceResult;
import com.example.h2_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    FileService fileService;

    @Autowired
    MailService mailService;

    public final RolesRepository rolesRepository;
    public final NotifyRepository notifyRepository;
    public final UserMapper userMapper;
    public final NotifyMapper notifyMapper;
    public UserServiceImpl(UserMapper userMapper,NotifyRepository notifyRepository,NotifyMapper notifyMapper, RolesRepository rolesRepository){
        this.rolesRepository=rolesRepository;
        this.userMapper=userMapper;
        this.notifyMapper=notifyMapper;
        this.notifyRepository=notifyRepository;
    }
    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAllUsers();
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
                            user.setResetCount(count++);
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
                                user.setPassword(newPass);
                                this.userRepository.save(user);
                                serviceResult.setMessage("Lấy lại mật khẩu thành công");
                                serviceResult.setStatus(HttpStatus.OK);
                                serviceResult.setData(newPass);

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

    public ServiceResult<UserDto> validateUser(UserDto userDto){

        ServiceResult<UserDto> userServiceResult = new ServiceResult<>();
        StringBuilder messError = new StringBuilder();

        //UserName
        if(userDto.getUsername().isEmpty()){
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
        if(userDto.getPassword().isEmpty()){
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
}
