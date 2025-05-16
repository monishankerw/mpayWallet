package com.mpayWallet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Pattern(regexp = "\\d{10}", message = "Mobile must be 10 digits")
    private String mobile;
    
    @NotBlank
    private String password;
}