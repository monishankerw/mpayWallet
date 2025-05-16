package com.mpayWallet.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillPaymentDto {

    private Integer billId;

    @NotNull(message = "Wallet ID is required")
    private Integer walletId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotBlank(message = "Bill type is required")
    private String billType;

    @NotNull
    @Positive(message = "Amount must be positive")
    private BigInteger amount;

    @PastOrPresent(message = "Payment date must be in the past or present")
    private Date paymentDate;
}