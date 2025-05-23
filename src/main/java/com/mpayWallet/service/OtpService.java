package com.mpayWallet.service;

public interface OtpService {
    void generateAndSendOtp( String mobile, String email);
    boolean verifyOtp(String identifier, String otp);
    void resendOtp(String identifier);


}