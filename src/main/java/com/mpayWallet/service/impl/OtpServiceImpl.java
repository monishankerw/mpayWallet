package com.mpayWallet.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mpayWallet.exception.OtpDeliveryException;
import com.mpayWallet.service.EmailService;
import com.mpayWallet.service.OtpService;
import com.mpayWallet.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final Cache<String, String> otpCache = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(10_000)
            .build();

    private final SmsService smsService;
    private final EmailService emailService;
    private final Random random = new Random();

    @Override
    public void generateAndSendOtp(String mobile, String email) {
        String otp = generateOtp();
        otpCache.put(mobile, otp);
        log.debug("OTP stored for mobile: {}", mobile);

        try {
            smsService.sendSms(mobile, "Your OTP is: " + otp);
            if (email != null && !email.isBlank()) {
                emailService.sendEmail(email, "OTP Verification", "Your OTP: " + otp);
            }
        } catch (Exception e) {
            otpCache.invalidate(mobile);
            log.error("OTP sending failed. Mobile: {}, Email: {}", mobile, email);
            throw new OtpDeliveryException("Failed to deliver OTP", e);
        }
    }



    @Override
    public boolean verifyOtp(String mobile, String otp) {
        String storedOtp = otpCache.getIfPresent(mobile);
        if (storedOtp == null) {
            log.warn("OTP expired or not found for mobile: {}", mobile);
            return false;
        }

        boolean isValid = storedOtp.equals(otp);
        if (isValid) {
            otpCache.invalidate(mobile);
            log.info("OTP validated successfully for mobile: {}", mobile);
        } else {
            log.warn("Invalid OTP attempt for mobile: {}", mobile);
        }
        return isValid;
    }

    @Override
    public void resendOtp(String identifier) {

    }

    private String generateOtp() {
        String otp = String.format("%06d", random.nextInt(999999));
        log.debug("Generated OTP: {}", otp);
        return otp;
    }
}