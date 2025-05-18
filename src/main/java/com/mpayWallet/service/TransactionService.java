package com.mpayWallet.service;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.TransactionDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    ApiResponse<TransactionDto> getTransactionById(Long txnId);
    ApiResponse<List<TransactionDto>> getTransactionsByType(String type);
    ApiResponse<List<TransactionDto>> getTransactionsByDate(LocalDateTime date);
}