package com.example.h2_shop.controller;

import com.example.h2_shop.model.EmailRequest;
import com.example.h2_shop.service.MailService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api")
public class MailController {
    @Autowired
    MailService mailService;

    @PostMapping("/forgot-password")
    public void forGotPassWord(@RequestBody EmailRequest request) throws MessagingException {
        mailService.sendMail(request.getTo(), request.getSubject(), request.getText());
    }
}
