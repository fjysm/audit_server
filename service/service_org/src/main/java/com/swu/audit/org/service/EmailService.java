package com.swu.audit.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String email, String username, String password) {
        String subject = "欢迎注册电力审计文本分类系统";
        String body = "感谢您注册我们的系统。\n\n"
                + "您的用户名是：" + username + "\n"
                + "您的初始密码是：" + password + "\n\n"
                + "请登录系统并尽快更改密码。\n"
                + "祝您使用愉快！";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1779937290@qq.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);

    }
}
