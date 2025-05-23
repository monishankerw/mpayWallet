package com.mpayWallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bank_account", uniqueConstraints = @UniqueConstraint(columnNames = "accountNumber"))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bccId;


    @NotNull(message = "Account number is required")
    @Positive(message = "Account number must be positive")
    private Long accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
    private String ifsc;

    @NotNull
    @PositiveOrZero(message = "Balance must be zero or positive")
    private Double balance;

    @Min(value = 6000000000L, message = "Mobile number must be valid")
    @Max(value = 9999999999L, message = "Mobile number must be valid")
    private Long mobile;

    @Version
    private int version;
}