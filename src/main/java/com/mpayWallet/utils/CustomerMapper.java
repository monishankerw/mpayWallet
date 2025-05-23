package com.mpayWallet.utils;

import com.mpayWallet.dto.CustomerOnboardingDto;
import com.mpayWallet.entity.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomerMapper {

    public Customer mapToCustomer(CustomerOnboardingDto dto) {
        return Customer.builder()
                .customerName(dto.getCustomerName())
                .customerAddress(dto.getCustomerAddress())
                .customerState(dto.getCustomerState())
                .mobile(dto.getMobile())
                .email(dto.getEmail())
                .state(dto.getState())
                .city(dto.getCity())
                .pinCode(dto.getPinCode())
                .country(dto.getCountry())
                .build();
    }

    public KycDetails mapToKycDetails(CustomerOnboardingDto dto) {
        return KycDetails.builder()
                .panNumber(dto.getPanNumber())
                .aadharNumber(dto.getAadharNumber())
                .address(dto.getAddress())
                .documentType(dto.getDocumentType())
                .documentUrl(dto.getFile())
                .build();
    }

    public BankAccount mapToBankAccount(CustomerOnboardingDto dto) {
        return BankAccount.builder()
                .bankName(dto.getBankName())
                .accountNumber(Long.parseLong(dto.getAccountNumber()))
                .ifsc(dto.getIfscCode())
                .bankName(dto.getBranchName())
                .build();
    }

    public BeneficiaryDetails mapToBeneficiary(CustomerOnboardingDto dto) {
        return BeneficiaryDetails.builder()
                .beneficiaryName(dto.getBeneficiaryName())
                .mobile(dto.getMobile())
                .build();
    }
}