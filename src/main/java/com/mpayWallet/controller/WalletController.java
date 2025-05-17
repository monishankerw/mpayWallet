package com.mpayWallet.controller;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.WalletDto;
import com.mpayWallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<WalletDto>> getWalletByCustomerId(@PathVariable Long customerId) {
        ApiResponse<WalletDto> response = walletService.getWalletByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }
}