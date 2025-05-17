package com.mpayWallet.controller;

import com.mpayWallet.dto.BankAccountDto;
import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank-account")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping
    public ResponseEntity<ApiResponse<BankAccountDto>> addBankAccount(@Valid @RequestBody BankAccountDto dto) {
        return ResponseEntity.ok(bankAccountService.addBankAccount(dto));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse<BankAccountDto>> getBankAccount(@PathVariable Long accountNumber) {
        return ResponseEntity.ok(bankAccountService.getBankAccountByAccountNumber(accountNumber));
    }
}