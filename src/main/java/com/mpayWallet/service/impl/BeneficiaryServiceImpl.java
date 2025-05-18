package com.mpayWallet.service.impl;

import com.mpayWallet.dto.*;
import com.mpayWallet.entity.BeneficiaryDetails;
import com.mpayWallet.entity.Customer;
import com.mpayWallet.entity.Wallet;
import com.mpayWallet.exception.ResourceNotFoundException;
import com.mpayWallet.repository.BeneficiaryRepository;
import com.mpayWallet.repository.CustomerRepository;
import com.mpayWallet.repository.WalletRepository;
import com.mpayWallet.service.BeneficiaryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;
    private final WalletRepository walletRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public ApiResponse<BeneficiaryResponse> addBeneficiary(BeneficiaryDetailsDto request) {
        log.info("Initiating addBeneficiary for walletId: {}, mobile: {}", request.getWalletId(), request.getMobile());

        Wallet wallet = walletRepository.findById(request.getWalletId())
                .orElseThrow(() -> {
                    log.error("Wallet not found for walletId: {}", request.getWalletId());
                    return new ResourceNotFoundException("Wallet not found");
                });

        Customer beneficiaryCustomer = customerRepository.findByMobile(request.getMobile())
                .orElseThrow(() -> {
                    log.error("No user found with mobile: {}", request.getMobile());
                    return new ResourceNotFoundException("No user registered with this mobile");
                });

        if (beneficiaryRepository.existsByWalletAndMobile(wallet, request.getMobile())) {
            log.warn("Beneficiary already exists for walletId: {} and mobile: {}", wallet.getWalletId(), request.getMobile());
            throw new RuntimeException("Beneficiary already added");
        }

        BeneficiaryDetails beneficiary = BeneficiaryDetails.builder()
                .beneficiaryName(request.getBeneficiaryName())
                .mobile(request.getMobile())
                .wallet(wallet)
                .build();

        beneficiaryRepository.save(beneficiary);
        log.info("Beneficiary saved with ID: {}", beneficiary.getBeneficiaryId());

        BeneficiaryResponse response = BeneficiaryResponse.builder()
                .beneficiaryId(beneficiary.getBeneficiaryId())
                .beneficiaryName(beneficiary.getBeneficiaryName())
                .mobile(beneficiary.getMobile())
                .walletId(wallet.getWalletId())
                .build();

        return new ApiResponse<>(true, "Beneficiary added successfully", response);
    }

    @Override
    public ApiResponse<List<BeneficiaryDetailsDto>> getAllBeneficiaries(Long walletId) {
        log.info("Fetching all beneficiaries for walletId: {}", walletId);

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> {
                    log.error("Wallet not found for walletId: {}", walletId);
                    return new ResourceNotFoundException("Wallet not found");
                });

        List<BeneficiaryDetails> list = beneficiaryRepository.findByWallet(wallet);

        List<BeneficiaryDetailsDto> dtoList = list.stream().map(b ->
                BeneficiaryDetailsDto.builder()
                        .beneficiaryId(b.getBeneficiaryId())
                        .beneficiaryName(b.getBeneficiaryName())
                        .mobile(b.getMobile())
                        .build()
        ).collect(Collectors.toList());

        log.info("Total {} beneficiaries fetched for walletId: {}", dtoList.size(), walletId);

        return new ApiResponse<>(true, "Fetched successfully", dtoList);
    }
}