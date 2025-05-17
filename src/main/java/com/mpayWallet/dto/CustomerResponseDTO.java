package com.mpayWallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDTO {
    private Long customerId;
    private String customerName;
    private String email;
    private Long mobile;
    private Double walletBalance;
}