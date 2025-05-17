package com.mpayWallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BeneficiaryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long beneficiaryId;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @NotBlank(message = "Beneficiary name is required")
    private String beneficiaryName;

    @Pattern(regexp = "\\d{10}", message = "Mobile must be 10 digits")
    private Long beneficiaryMobile;

}