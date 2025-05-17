package com.mpayWallet.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Min(value = 6000000000L, message = "Mobile number must be valid")
    @Max(value = 9999999999L, message = "Mobile number must be valid")
    private Long mobile;
    
    @NotBlank
    private String password;
}