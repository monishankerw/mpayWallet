package com.mpayWallet.controller;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.TransactionDto;
import com.mpayWallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/by-id/{txnId}")
    public ApiResponse<TransactionDto> getTransactionById(@PathVariable Long txnId) {
        return transactionService.getTransactionById(txnId);
    }

    @GetMapping("/by-type")
    public ApiResponse<List<TransactionDto>> getTransactionsByType(@RequestParam String type) {
        return transactionService.getTransactionsByType(type);
    }

    @GetMapping("/by-date")
    public ResponseEntity<ApiResponse<List<TransactionDto>>> getByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        ApiResponse<List<TransactionDto>> response = transactionService.getTransactionsByDate(date);
        return ResponseEntity.ok(response);
    }
}