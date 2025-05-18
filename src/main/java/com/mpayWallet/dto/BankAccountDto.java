package com.mpayWallet.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccountDto {


    @NotNull(message = "Account number is required")
    @Positive(message = "Account number must be positive")
    private Long accountNumber;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
    private String ifsc;

    @NotNull
    @PositiveOrZero(message = "Balance must be zero or positive")
    private Long balance;

    @Min(value = 6000000000L, message = "Mobile number must be valid")
    @Max(value = 9999999999L, message = "Mobile number must be valid")
    private Long mobile;

    @NotNull(message = "Wallet ID is required")
    private Long walletId;
}