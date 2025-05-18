package com.mpayWallet.service.impl;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.TransactionDto;
import com.mpayWallet.entity.Transaction;
import com.mpayWallet.exception.ResourceNotFoundException;
import com.mpayWallet.repository.TransactionRepository;
import com.mpayWallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public ApiResponse<TransactionDto> getTransactionById(Long txnId) {
        log.info("Fetching transaction by ID: {}", txnId);
        Transaction txn = transactionRepository.findById(txnId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + txnId));
        return new ApiResponse<>(true, "Transaction found", mapToDto(txn));
    }

    @Override
    public ApiResponse<List<TransactionDto>> getTransactionsByType(String type) {
        log.info("Fetching transactions by type: {}", type);
        List<Transaction> list = transactionRepository.findByTransactionType(type.toUpperCase());
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No transactions found with type: " + type);
        }
        return new ApiResponse<>(true, "Transactions fetched", mapListToDto(list));
    }

    @Override
    public ApiResponse<List<TransactionDto>> getTransactionsByDate(LocalDateTime date) {
        LocalDateTime startOfDay = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = date.withHour(23).withMinute(59).withSecond(59);

        List<Transaction> list = transactionRepository.findByTransactionDateBetween(
                Timestamp.valueOf(startOfDay),
                Timestamp.valueOf(endOfDay)
        );

        List<TransactionDto> dtos = list.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new ApiResponse<>(true, "Transactions fetched successfully", dtos);
    }
    private TransactionDto mapToDto(Transaction txn) {
        return TransactionDto.builder()
                .transactionId(txn.getTransactionId())
                .walletId(txn.getWallet().getWalletId())
                .transactionType(txn.getTransactionType())
                .amount(txn.getAmount())
                .transactionDate(txn.getTransactionDate())
                .transactionDesc(txn.getTransactionDesc())
                .build();
    }

    private List<TransactionDto> mapListToDto(List<Transaction> txns) {
        return txns.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}