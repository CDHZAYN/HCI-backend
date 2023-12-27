package com.example.hci.service.impl;

import com.example.hci.exception.MyException;
import com.example.hci.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;


@Service
public class EmailServiceImpl implements EmailService {
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendMessageToEmail(String verifyCode, String email) {
        Context context = new Context();
        context.setVariable("verifyCode", Arrays.asList(verifyCode.split("")));
        String process = templateEngine.process("EmailVerificationCode.html", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("验证码");
            helper.setFrom(username);
            helper.setTo(email);
            helper.setSentDate(new Date());
            helper.setText(process, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new MyException("ES0001", "邮件发送异常");
        }
    }

    public String generateRandomString() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        // 生成三组数字，每组包含三位数
        for (int i = 0; i < 3; i++) {
            int randomNumber = random.nextInt(1000);
            stringBuilder.append(String.format("%03d", randomNumber));

            // 在每两组数字之间添加短横线
            if (i < 2) {
                stringBuilder.append("-");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void sendBookMessage(String email, String subject, String content) {
        if(!StringUtils.isEmpty(email)) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setSubject(subject);
                helper.setFrom(username);
                helper.setTo(email);
                helper.setSentDate(new Date());
                helper.setText(content, false);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                throw new MyException("ES0001", "邮件发送异常");
            }
        }
    }
}
