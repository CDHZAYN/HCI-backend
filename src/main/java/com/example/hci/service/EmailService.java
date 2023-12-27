package com.example.hci.service;

public interface EmailService {
    void sendMessageToEmail(String verifyCode, String email);

    void sendBookMessage(String email, String subject, String content);

    String generateRandomString();
}
