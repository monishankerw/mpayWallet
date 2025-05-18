package com.mpayWallet.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BeneficiaryResponse {

    private Long beneficiaryId;
    private Long walletId;
    @NotBlank(message = "Beneficiary name is required")
    private String beneficiaryName;
    private Long mobile;
    private String bankAccount;
    private String ifscCode;

}