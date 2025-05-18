package com.mpayWallet.controller;

import com.mpayWallet.dto.*;
import com.mpayWallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
    @PostMapping("/bank-to-wallet/{walletId}")
    public ResponseEntity<ApiResponse<BankToWalletResponse>> fundWallet(
            @PathVariable(required = true) Long walletId,
            @RequestBody @Valid BankToWalletRequest dto) {

        log.info("Received bank-to-wallet request. walletId: {}, requestBody: {}", walletId, dto);

        if (walletId == null) {
            log.error("walletId is null in the path variable!");
            throw new IllegalArgumentException("Wallet ID must not be null");
        }

        ApiResponse<BankToWalletResponse> response = walletService.transferFromBank(walletId, dto);

        log.info("Wallet successfully funded. Response: {}", response);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/wallet/fund-by-mobile")
    public ResponseEntity<ApiResponse<AddMoneyResponse>> addMoneyByMobile(@RequestBody @Valid AddMoneyRequest request) {
        log.info("Request received to add money via mobile number: {}", request.getMobileNumber());
        return ResponseEntity.ok(walletService.addMoneyByMobile(request));
    }
}