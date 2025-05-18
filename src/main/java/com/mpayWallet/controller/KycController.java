package com.mpayWallet.controller;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.KycDetailsDto;
import com.mpayWallet.service.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/kyc")
@RequiredArgsConstructor
public class KycController {

    private final KycService kycService;

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<String>> submitKyc(@RequestPart KycDetailsDto dto,
                                                         @RequestPart MultipartFile file) {
        return ResponseEntity.ok(kycService.submitKyc(dto, file));
    }

    @GetMapping("/all")
    public ResponseEntity<List<KycDetailsDto>> getAllKyc() {
        return ResponseEntity.ok(kycService.getAllKyc());
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ApiResponse<String>> updateKycStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(kycService.updateKycStatus(id, status));
    }
}
