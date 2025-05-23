package com.mpayWallet.entity;

import com.mpayWallet.utils.KycLevel;
import com.mpayWallet.utils.KycStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

//    @NotNull
//    @PositiveOrZero(message = "Balance must be zero or positive")
//    private Double balance;

    @OneToOne(mappedBy = "wallet")
    private Customer customer;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<BillPayment> billPayments;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<BeneficiaryDetails> beneficiaries;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;

    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal withdrawalLimit = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private KycLevel kycLevel;

    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus;

}