package com.example.hci.service;

public interface EmailService {
    void sendMessageToEmail(String verifyCode, String email);
}
