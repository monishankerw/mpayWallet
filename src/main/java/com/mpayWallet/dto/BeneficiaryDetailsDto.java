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

    @Pattern(regexp = "\\d{10}", message = "Beneficiary mobile must be 10 digits")
    private Long beneficiaryMobile;
}