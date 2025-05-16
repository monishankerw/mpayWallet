package com.mpayWallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer walletId;

    @NotNull
    @PositiveOrZero(message = "Balance must be zero or positive")
    private double balance;

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

}