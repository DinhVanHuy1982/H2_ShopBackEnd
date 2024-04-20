package com.example.h2_shop.service;

import javax.mail.MessagingException;

public interface MailService {
    public void sendMail(String to, String title, String text) throws MessagingException;

}
