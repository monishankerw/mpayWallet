package com.mpayWallet.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerOnboardingDto {
    //customer Details
    private Long customerId;
    private String customerName;
    private String customerAddress;
    private String customerState;
    private Long mobile;
    private String email;
    private String state;
    private String city;
    private String pinCode;
    private String country;
    //KYC Details
    private String panNumber;
    private String aadharNumber;
    private String address;
    private String documentType;
    private String file;
    private String status;
    private Date submittedDate;
    //Bank Details
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String branchName;
    private String bankAddress;
    private String bankState;
    private String bankCity;
    private String bankPinCode;
    private String bankCountry;
    private String bankMobile;
    private String bankEmail;
    private String accountHolderName;

    //OTP
    private String otp;

    private String beneficiaryName;

}
