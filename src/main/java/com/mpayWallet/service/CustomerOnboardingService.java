package com.mpayWallet.service;

import com.mpayWallet.dto.CustomerOnboardingDto;
import com.mpayWallet.dto.CustomerResponseDTO;

public interface CustomerOnboardingService {
    CustomerResponseDTO onboardCustomer(CustomerOnboardingDto dto);
}