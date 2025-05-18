package com.mpayWallet.controller;


import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.BeneficiaryDetailsDto;
import com.mpayWallet.dto.BeneficiaryResponse;
import com.mpayWallet.service.BeneficiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/beneficiaries")
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<BeneficiaryResponse>> addBeneficiary(
            @Valid @RequestBody BeneficiaryDetailsDto request) {

        ApiResponse<BeneficiaryResponse> response = beneficiaryService.addBeneficiary(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<ApiResponse<List<BeneficiaryDetailsDto>>> getAll(@PathVariable Long walletId) {
        log.info("Fetching beneficiaries for walletId: {}", walletId);
        return ResponseEntity.ok(beneficiaryService.getAllBeneficiaries(walletId));
    }
}