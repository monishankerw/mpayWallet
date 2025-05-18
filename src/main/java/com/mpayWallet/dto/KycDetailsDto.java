package com.mpayWallet.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KycDetailsDto {

    private Long kycId;

    @NotNull(message = "customer Id is required")
    private Long customerId;

    @NotBlank(message = "PAN number is required")
    private String panNumber;

    @NotBlank(message = "Aadhar number is required")
    private String aadharNumber;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Document type is required")
    private String documentType;

    private String documentUrl;

    private String status;

    private Date submittedDate;
}
