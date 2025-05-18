package com.mpayWallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyResponse {
    private Long walletId;
    private Long mobileNumber;
    private Double updatedBalance;
    private String message;
}