package com.mpayWallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BillPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Bill type is required")
    private String billType;

    @NotNull
    @Positive(message = "Amount must be positive")
    private Double amount;

    @PastOrPresent(message = "Payment date cannot be in the future")
    private Date paymentDate;

}