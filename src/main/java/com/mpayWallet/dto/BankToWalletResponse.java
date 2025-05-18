package com.mpayWallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BankToWalletResponse {
    private Long walletId;
    private Long bankAccountId;
    private Long amount;
    private String message;
}
