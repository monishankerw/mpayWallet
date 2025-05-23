package com.mpayWallet.dto;

import com.mpayWallet.utils.KycStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

        private Long walletId;
        private String status;
        private KycStatus kycStatus;
        private BigDecimal withdrawalLimit;
        private LocalDateTime onboardedAt;
    }
