package com.mpayWallet.controller;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.exception.OtpVerificationException;
import com.mpayWallet.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;

    @PostMapping("/initiate-onboarding")
    public ResponseEntity<ApiResponse<Void>> initiateOnboarding(
            @RequestParam String mobile,
            @RequestParam(required = false) String email) {

        otpService.generateAndSendOtp(mobile, email);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "OTP sent successfully", null)
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<Void>> verifyOtp(
            @RequestParam String mobile,
            @RequestParam String otp) {

        boolean isValid = otpService.verifyOtp(mobile, otp);
        if (!isValid) {
            throw new OtpVerificationException("Invalid OTP");
        }
        return ResponseEntity.ok(
                new ApiResponse<>(true, "OTP verified successfully", null)
        );
    }
}