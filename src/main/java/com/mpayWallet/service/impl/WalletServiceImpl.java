package com.mpayWallet.service.impl;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.WalletDto;
import com.mpayWallet.entity.Customer;
import com.mpayWallet.entity.Wallet;
import com.mpayWallet.exception.ResourceNotFoundException;
import com.mpayWallet.repository.CustomerRepository;
import com.mpayWallet.repository.WalletRepository;
import com.mpayWallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final CustomerRepository customerRepository;

    @Override
    public ApiResponse<WalletDto> getWalletByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        Wallet wallet = customer.getWallet();
        if (wallet == null) {
            throw new ResourceNotFoundException("Wallet not found for customer ID: " + customerId);
        }

        WalletDto dto = WalletDto.builder()
                .walletId(wallet.getWalletId())
                .balance((long) wallet.getBalance())
                .customerId(customerId)
                .build();

        return new ApiResponse<>(true, "Wallet fetched successfully", dto);
    }
}