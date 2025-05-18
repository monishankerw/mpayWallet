package com.mpayWallet.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiaryDetailsDto {

    private Long beneficiaryId;

    @NotNull(message = "Wallet ID is required")
    private Long walletId;

    @NotBlank(message = "Beneficiary name is required")
    private String beneficiaryName;

    @Min(value = 6000000000L, message = "Mobile number must be valid")
    @Max(value = 9999999999L, message = "Mobile number must be valid")
    private Long mobile;
}