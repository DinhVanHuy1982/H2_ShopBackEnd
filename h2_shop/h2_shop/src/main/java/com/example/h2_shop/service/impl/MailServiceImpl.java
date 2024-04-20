package com.example.h2_shop.service.impl;

import com.example.h2_shop.service.MailService;
import com.example.h2_shop.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {
    static final String from = "dinhvanhuytest@gmail.com";
    static final String password2 = "icaavcntmoyriicy";

    @Override
    public void sendMail(String to,String title, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication(from, password2);
            }
        };
        Session session = Session.getInstance(props, auth);
        // Tạo một tin nhắn
        MimeMessage msg = new MimeMessage(session);

        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

        // Người gửi
        msg.setFrom(from);

        // Người nhận
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

        // Tiêu đề email
        msg.setSubject(title);

        // Quy đinh ngày gửi
        msg.setSentDate(new Date());

        // Quy định email nhận phản hồi
        // msg.setReplyTo(InternetAddress.parse(from, false))
//        content = "<!DOCTYPE html>\n" +
//                "<html lang=\"en\">\n" +
//                "<head>\n" +
//                "    <meta charset=\"UTF-8\">\n" +
//                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
//                "    <title>Document</title>\n" +
//                "    <style>\n" +
//                "        *{\n" +
//                "            font-family: Inter, mySymbol !important;;\n" +
//                "        }\n" +
//                "        .opt{\n" +
//                "            color: blue;\n" +
//                "            font-weight: 600;\n" +
//                "        }\n" +
//                "    </style>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "    <h1>H2Shop</h1>\n" +
//                "    <div>Kính gửi quý khách hàng: Đinh Văn Huy</div>\n" +
//                "    <div>\n" +
//                "        H2Shop trân trọng thông báo mã OTP lấy lại của quý khách là <span style=\"color: blue;font-weight: 600;\" class=\"opt\">&nbsp;123456&nbsp;</span> vui lòng nhập mã sau để thực hiện khôi phục mật khẩu. Trân trọng!\n" +
//                "    </div>\n" +
//                "</body>\n" +
//                "</html>";

        // Nội dung
        msg.setContent(content, "text/HTML; charset=UTF-8");


        Transport.send(msg);
    }
}
