package com.mpayWallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @NotBlank(message = "Transaction type is required")
    private String transactionType;

    @NotNull
    @Positive(message = "Amount must be positive")
    private Double amount;

    @PastOrPresent(message = "Transaction date cannot be in the future")
    private Date transactionDate;

    private String transactionDesc;
}