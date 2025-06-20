package com.mpayWallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class BeneficiaryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long beneficiaryId;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @NotBlank(message = "Beneficiary name is required")
    private String beneficiaryName;

    @Min(value = 6000000000L, message = "Mobile number must be valid")
    @Max(value = 9999999999L, message = "Mobile number must be valid")
    private Long mobile;

}