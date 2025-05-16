package com.mpayWallet.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccountDto {

    private Integer accountNumber;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
    private String IFSC;

    @NotNull
    @PositiveOrZero(message = "Balance must be zero or positive")
    private BigInteger balance;

    @Pattern(regexp = "\\d{10}", message = "Mobile must be 10 digits")
    private String mobile;

    @NotNull(message = "Wallet ID is required")
    private Integer walletId;
}