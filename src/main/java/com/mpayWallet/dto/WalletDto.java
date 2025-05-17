package com.mpayWallet.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletDto {

    private Long walletId;

    @NotNull
    @PositiveOrZero(message = "Balance must be zero or positive")
    private Long balance;

    @NotNull(message = "Customer ID is required")
    private Long customerId;
}