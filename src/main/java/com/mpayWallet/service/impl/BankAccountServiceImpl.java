package com.mpayWallet.service.impl;

import com.mpayWallet.dto.BankAccountDto;
import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.entity.BankAccount;
import com.mpayWallet.entity.Wallet;
import com.mpayWallet.repository.BankAccountRepository;
import com.mpayWallet.repository.WalletRepository;
import com.mpayWallet.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository repository;
    private final WalletRepository walletRepository;

    @Override
    public ApiResponse<BankAccountDto> addBankAccount(BankAccountDto dto) {
        Optional<Wallet> optionalWallet = walletRepository.findById(dto.getWalletId());
        if (optionalWallet.isEmpty()) {
            return new ApiResponse<>(false, "Wallet not found", null);
        }

        BankAccount account = new BankAccount();
        account.setAccountNumber(dto.getAccountNumber());
        account.setWallet(optionalWallet.get());
        account.setBankName(dto.getBankName());
        account.setIfsc(dto.getIfsc());
        account.setBalance(dto.getBalance());
        account.setMobile(dto.getMobile());

        BankAccount saved = repository.save(account);

        dto.setAccountNumber(saved.getAccountNumber());
        return new ApiResponse<>(true, "Bank account added successfully", dto);
    }

    @Override
    public ApiResponse<BankAccountDto> getBankAccountByAccountNumber(Long accountNumber) {
        Optional<BankAccount> optionalAccount = repository.findByAccountNumber(accountNumber);
        if (optionalAccount.isEmpty()) {
            return new ApiResponse<>(false, "Account not found", null);
        }

        BankAccount acc = optionalAccount.get();
        BankAccountDto dto = BankAccountDto.builder()
                .accountNumber(acc.getAccountNumber())
                .bankName(acc.getBankName())
                .ifsc(acc.getIfsc())
                .balance(acc.getBalance())
                .mobile(acc.getMobile())
                .walletId(acc.getWallet().getWalletId())
                .build();

        return new ApiResponse<>(true, "Bank account found", dto);
    }
}