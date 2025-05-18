package com.mpayWallet.service.impl;

import com.mpayWallet.dto.*;
import com.mpayWallet.entity.BankAccount;
import com.mpayWallet.entity.Customer;
import com.mpayWallet.entity.Transaction;
import com.mpayWallet.entity.Wallet;
import com.mpayWallet.exception.InsufficientBalanceException;
import com.mpayWallet.exception.ResourceNotFoundException;
import com.mpayWallet.repository.BankAccountRepository;
import com.mpayWallet.repository.CustomerRepository;
import com.mpayWallet.repository.TransactionRepository;
import com.mpayWallet.repository.WalletRepository;
import com.mpayWallet.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;


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
                .balance(wallet.getBalance())
                .customerId(customerId)
                .build();

        return new ApiResponse<>(true, "Wallet fetched successfully", dto);
    }

    @Transactional
    @Override
    public ApiResponse<BankToWalletResponse> transferFromBank(Long walletId, BankToWalletRequest dto) {
        log.info("Initiating transfer from bank to wallet. walletId: {}, dto: {}", walletId, dto);

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> {
                    log.error("Wallet not found with ID: {}", walletId);
                    return new ResourceNotFoundException("Wallet not found with id: " + walletId);
                });

        log.info("Wallet found: walletId={}, balance={}", wallet.getWalletId(), wallet.getBalance());

        BankAccount bank = bankAccountRepository.findById(dto.getBccId())
                .orElseThrow(() -> {
                    log.error("Bank Account not found with ID: {}", dto.getBccId());
                    return new ResourceNotFoundException("Bank Account not found with id: " + dto.getBccId());
                });

        log.info("Bank Account found: bankId={}, balance={}", bank.getBccId(), bank.getBalance());

        if (bank.getBalance() < dto.getAmount()) {
            log.error("Insufficient bank balance. Required={}, Available={}", dto.getAmount(), bank.getBalance());
            throw new InsufficientBalanceException("Insufficient bank balance");
        }

        // Debit from bank and credit to wallet
        bank.setBalance(bank.getBalance() - dto.getAmount());
        wallet.setBalance(wallet.getBalance() + dto.getAmount());

        log.info("Debited bank and credited wallet. New bank balance={}, new wallet balance={}",
                bank.getBalance(), wallet.getBalance());

        Transaction txn = Transaction.builder()
                .transactionType("CREDIT")
                .amount(dto.getAmount())
                .transactionDesc("Bank to Wallet")
                .wallet(wallet)
                .transactionDate(new java.util.Date())
                .build();

        transactionRepository.save(txn);
        bankAccountRepository.save(bank);
        walletRepository.save(wallet);

        log.info("Transaction saved successfully. txnId={}, amount={}", txn.getTransactionId(), txn.getAmount());

        BankToWalletResponse response = new BankToWalletResponse(
                wallet.getWalletId(),
                bank.getBccId(),
                dto.getAmount(),
                "Wallet funded successfully"
        );

        log.info("Returning success response: {}", response);
        return new ApiResponse<>(true, "Success", response);
    }
    @Transactional
    @Override
    public ApiResponse<AddMoneyResponse> addMoneyByMobile(AddMoneyRequest request) {
        log.info("Finding customer by mobile number: {}", request.getMobileNumber());
        Customer customer = customerRepository.findByMobile(request.getMobileNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with mobile: " + request.getMobileNumber()));

        Wallet wallet = customer.getWallet();
        if (wallet == null) {
            throw new ResourceNotFoundException("Wallet not found for customer with mobile: " + request.getMobileNumber());
        }

        log.info("Current wallet balance: {}", wallet.getBalance());
        wallet.setBalance(wallet.getBalance() + request.getAmount());
        walletRepository.save(wallet);
        log.info("Updated wallet balance: {}", wallet.getBalance());

        Transaction txn = Transaction.builder()
                .transactionType("CREDIT")
                .amount(request.getAmount())
                .transactionDesc("Added money via mobile number")
                .wallet(wallet)
                .transactionDate(new Date())
                .build();

        transactionRepository.save(txn);
        log.info("Transaction created for walletId {} with amount {}", wallet.getWalletId(), request.getAmount());

        AddMoneyResponse response = new AddMoneyResponse(
                wallet.getWalletId(),
                request.getMobileNumber(),
                wallet.getBalance(),
                "Money added successfully"
        );

        return new ApiResponse<>(true, "Success", response);
    }
}
