package com.mpayWallet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyRequest {

    @NotBlank(message = "Mobile number is required")
    private Long mobileNumber;

    @Min(value = 1, message = "Amount must be greater than zero")
    private Double amount;
}