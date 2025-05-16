package com.mpayWallet.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletDto {

    private Integer walletId;

    @NotNull
    @PositiveOrZero(message = "Balance must be zero or positive")
    private BigInteger balance;

    @NotNull(message = "Customer ID is required")
    private Integer customerId;
}