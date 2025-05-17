package com.mpayWallet.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

    private Long transactionId;

    @NotNull(message = "Wallet ID is required")
    private Long walletId;

    @NotBlank(message = "Transaction type is required")
    private String transactionType;

    @NotNull
    @Positive(message = "Amount must be positive")
    private Long amount;

    @PastOrPresent(message = "Transaction date must be in the past or present")
    private Date transactionDate;

    private String transactionDesc;
}