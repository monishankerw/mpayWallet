package com.mpayWallet.service.impl;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.CustomerDto;
import com.mpayWallet.dto.CustomerResponseDTO;
import com.mpayWallet.entity.Customer;
import com.mpayWallet.entity.Wallet;
import com.mpayWallet.exception.CustomerAlreadyExistsException;
import com.mpayWallet.repository.CustomerRepository;
import com.mpayWallet.repository.WalletRepository;
import com.mpayWallet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new customer with the given information.
     *
     * @param dto The customer information to register.
     * @return An ApiResponse containing a CustomerResponseDTO, which contains the
     *         customer's ID, name, email, mobile, and wallet balance.
     * @throws CustomerAlreadyExistsException If a customer with the same mobile
     *                                         already exists.
     */
    @Override
    public ApiResponse<CustomerResponseDTO> registerCustomer(CustomerDto dto) {
        log.info("Registering customer with mobile: {}", dto.getMobile());

        // Check if mobile already exists
        if (customerRepository.existsByMobile(dto.getMobile())) {
            log.warn("Mobile already exists: {}", dto.getMobile());
            throw new CustomerAlreadyExistsException("Mobile already registered: " + dto.getMobile());
        }

        // Create new customer
        Customer customer = Customer.builder()
                .customerName(dto.getCustomerName())
                .customerAddress(dto.getCustomerAddress())
                .customerState(dto.getCustomerState())
                .email(dto.getEmail())
                .mobile(dto.getMobile())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        // Create and link wallet
        Wallet wallet = new Wallet();
        wallet.setBalance(0.0);
        wallet.setCustomer(customer);
        customer.setWallet(wallet);

        // Save customer (cascade should save wallet)
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer saved with ID: {}", savedCustomer.getCustomerId());

        // Build response
        CustomerResponseDTO response = CustomerResponseDTO.builder()
                .customerId(savedCustomer.getCustomerId())
                .customerName(savedCustomer.getCustomerName())
                .email(savedCustomer.getEmail())
                .mobile((savedCustomer.getMobile()))
                .walletBalance(Double.valueOf(savedCustomer.getWallet().getBalance()))
                .build();

        return new ApiResponse<>(true, "Registration successful", response);
    }
}