package com.mpayWallet.service.impl;

import com.mpayWallet.dto.CustomerOnboardingDto;
import com.mpayWallet.dto.CustomerResponseDTO;
import com.mpayWallet.entity.*;
import com.mpayWallet.exception.DuplicateCustomerException;
import com.mpayWallet.exception.DuplicateKycException;
import com.mpayWallet.exception.OtpVerificationException;
import com.mpayWallet.repository.*;
import com.mpayWallet.service.CustomerOnboardingService;
import com.mpayWallet.service.OtpService;
import com.mpayWallet.utils.CustomerMapper;
import com.mpayWallet.utils.KycStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerOnboardingServiceImpl implements CustomerOnboardingService {

    private final CustomerRepository customerRepository;
    private final KycDetailsRepository kycDetailsRepository;
    private final BankAccountRepository bankAccountRepository;
    private final WalletRepository walletRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final OtpService otpService;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDTO onboardCustomer(CustomerOnboardingDto dto) {
        log.info("Starting customer onboarding process for mobile: {}", dto.getMobile());

        try {
            validateOtp(dto.getMobile().toString(), dto.getOtp());
            validateUniqueConstraints(dto);

            Customer customer = saveCustomer(dto);
            log.debug("Customer created with ID: {}", customer.getCustomerId());

            Wallet wallet = createWallet(customer);
            log.debug("Wallet created with ID: {} for customer: {}", wallet.getWalletId(), customer.getCustomerId());

            saveKycDetails(dto, customer);
            log.debug("KYC details saved for customer: {}", customer.getCustomerId());

            saveBankAccount(dto, wallet);
            log.debug("Bank account linked to wallet: {}", wallet.getWalletId());

            saveBeneficiary(dto, wallet);

            CustomerResponseDTO response = buildResponse(customer, wallet);
            log.info("Customer onboarding completed successfully for customer ID: {}", customer.getCustomerId());

            return response;
        } catch (Exception e) {
            log.error("Customer onboarding failed for mobile: {}. Reason: {}", dto.getMobile(), e.getMessage(), e);
            throw e;
        }
    }

    private void validateOtp(String mobile, String otp) {
        log.debug("Validating OTP for mobile: {}", mobile);
        if (!otpService.verifyOtp(mobile, otp)) {
            log.warn("OTP verification failed for mobile: {}", mobile);
            throw new OtpVerificationException("OTP verification failed for mobile: " + mobile);
        }
        log.info("OTP validated successfully for mobile: {}", mobile);
    }

    private void validateUniqueConstraints(CustomerOnboardingDto dto) {
        log.debug("Checking unique constraints for customer onboarding");

        if (customerRepository.existsByMobile(dto.getMobile())) {
            log.warn("Duplicate mobile number detected: {}", dto.getMobile());
            throw new DuplicateCustomerException("Mobile already registered: " + dto.getMobile());
        }
        if (customerRepository.existsByEmail(dto.getEmail())) {
            log.warn("Duplicate email detected: {}", dto.getEmail());
            throw new DuplicateCustomerException("Email already registered: " + dto.getEmail());
        }
        if (kycDetailsRepository.existsByPanNumber(dto.getPanNumber())) {
            log.warn("Duplicate PAN number detected: {}", dto.getPanNumber());
            throw new DuplicateKycException("PAN already exists: " + dto.getPanNumber());
        }
        if (kycDetailsRepository.existsByAadharNumber(dto.getAadharNumber())) {
            log.warn("Duplicate Aadhar number detected: {}", dto.getAadharNumber());
            throw new DuplicateKycException("Aadhar already exists: " + dto.getAadharNumber());
        }

        log.info("All unique constraints validated successfully");
    }

    private Customer saveCustomer(CustomerOnboardingDto dto) {
        log.debug("Mapping and saving customer entity");
        Customer customer = customerMapper.mapToCustomer(dto);
        customer = customerRepository.save(customer);
        log.info("Customer saved successfully with ID: {}", customer.getCustomerId());
        return customer;
    }

    private Wallet createWallet(Customer customer) {
        log.debug("Creating wallet for customer ID: {}", customer.getCustomerId());
        Wallet wallet = Wallet.builder()
                .customer(customer)
                .kycStatus(KycStatus.PARTIAL)
                .withdrawalLimit(new BigDecimal("10000"))
                .balance(BigDecimal.ZERO)
                .build();
        wallet = walletRepository.save(wallet);
        log.info("Wallet created with ID: {} for customer ID: {}", wallet.getWalletId(), customer.getCustomerId());
        return wallet;
    }

    private void saveKycDetails(CustomerOnboardingDto dto, Customer customer) {
        log.debug("Processing KYC details for customer ID: {}", customer.getCustomerId());
        KycDetails kyc = customerMapper.mapToKycDetails(dto);
        kyc.setCustomerId(customer.getCustomerId());
        kyc.setStatus(String.valueOf(KycStatus.PENDING));
        kyc.setSubmittedDate(new Date());
        kycDetailsRepository.save(kyc);
        log.info("KYC details saved with ID: {} for customer ID: {}", kyc.getKycId(), customer.getCustomerId());
    }

    private void saveBankAccount(CustomerOnboardingDto dto, Wallet wallet) {
        log.debug("Processing bank account for wallet ID: {}", wallet.getWalletId());
        BankAccount account = customerMapper.mapToBankAccount(dto);
        account.setWallet(wallet);
        bankAccountRepository.save(account);
        log.info("Bank account saved with ID: {} for wallet ID: {}", account.getBccId(), wallet.getWalletId());
    }

    private void saveBeneficiary(CustomerOnboardingDto dto, Wallet wallet) {
        if (dto.getBeneficiaryName() != null && dto.getMobile() != null) {
            log.debug("Processing beneficiary details for wallet ID: {}", wallet.getWalletId());
            BeneficiaryDetails beneficiary = customerMapper.mapToBeneficiary(dto);
            beneficiary.setWallet(wallet);
            beneficiaryRepository.save(beneficiary);
            log.info("Beneficiary saved with ID: {} for wallet ID: {}",
                    beneficiary.getBeneficiaryId(), wallet.getWalletId());
        } else {
            log.debug("No beneficiary details provided for wallet ID: {}", wallet.getWalletId());
        }
    }

    private CustomerResponseDTO buildResponse(Customer customer, Wallet wallet) {
        log.debug("Building response DTO for customer ID: {}", customer.getCustomerId());
        CustomerResponseDTO response = CustomerResponseDTO.builder()
                .customerId(customer.getCustomerId())
                .walletId(wallet.getWalletId())
                .status("ONBOARDING_COMPLETED")
                .kycStatus(KycStatus.valueOf(wallet.getKycStatus().name()))
                .withdrawalLimit(wallet.getWithdrawalLimit())
                .onboardedAt(LocalDateTime.now())
                .build();
        log.info("Response DTO created for customer ID: {}", customer.getCustomerId());
        return response;
    }
}