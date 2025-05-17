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

    private Long billId;

    @NotNull(message = "Wallet ID is required")
    private Long walletId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Bill type is required")
    private String billType;

    @NotNull
    @Positive(message = "Amount must be positive")
    private Long amount;

    @PastOrPresent(message = "Payment date must be in the past or present")
    private Date paymentDate;
}