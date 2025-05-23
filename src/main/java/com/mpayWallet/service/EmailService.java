package com.mpayWallet.service;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}
