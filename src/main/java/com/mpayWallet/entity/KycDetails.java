package com.mpayWallet.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import jakarta.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KycDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kycId;

    @NotNull(message = "customerId is required")
    private Long customerId;

    @NotBlank(message = "PAN number is required")
    @Column(unique = true)
    private String panNumber;

    @NotBlank(message = "Aadhar number is required")
    @Column(unique = true)
    private String aadharNumber;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Document type is required")
    private String documentType;

    @NotBlank(message = "Document URL is required")
    private String documentUrl;

    @NotBlank(message = "Status is required")
    private String status; // PENDING, APPROVED, REJECTED

    @PastOrPresent(message = "Submitted date cannot be in the future")
    private Date submittedDate;
}