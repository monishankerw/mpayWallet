package com.mpayWallet.controller;

import com.mpayWallet.dto.*;
import com.mpayWallet.service.CustomerOnboardingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerOnboardingController {

    private final CustomerOnboardingService onboardingService;

    @PostMapping("/onboard")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> onboardCustomer(
            @Valid @RequestBody CustomerOnboardingDto dto) {

        CustomerResponseDTO response = onboardingService.onboardCustomer(dto);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,  // boolean success
                        "Customer onboarded successfully",
                        response
                )
        );
    }
}