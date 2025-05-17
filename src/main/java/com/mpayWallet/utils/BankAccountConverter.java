package com.mpayWallet.utils;

import com.mpayWallet.dto.BankAccountDto;
import com.mpayWallet.entity.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class BankAccountConverter {

    public static BankAccountDto toDto(BankAccount entity) {
        if (entity == null) {
            return null;
        }
        return BankAccountDto.builder()
                .accountNumber(entity.getAccountNumber())
                .bankName(entity.getBankName())
                .IFSC(entity.getIFSC())
                .balance(entity.getBalance())
                // Assuming entity.getMobile() is a String or BigInteger, safely convert to Long
                .mobile(entity.getMobile() != null ? Long.valueOf(entity.getMobile()) : null)
                // Handle wallet null and walletId null safely
                .walletId(entity.getWallet() != null && entity.getWallet().getWalletId() != null
                        ? entity.getWallet().getWalletId()
                        : null)
                .build();
    }

    public static BankAccount toEntity(BankAccountDto dto) {
        if (dto == null) {
            return null;
        }
        BankAccount entity = new BankAccount();
        entity.setAccountNumber(dto.getAccountNumber());
        entity.setBankName(dto.getBankName());
        entity.setIFSC(dto.getIFSC());
        entity.setBalance(dto.getBalance());
        // Convert Long mobile to String (or BigInteger) based on your entity field type
        entity.setMobile(dto.getMobile() != null ? dto.getMobile() : null);
        // Wallet assignment should be done elsewhere if needed, as dto doesn't have Wallet object
        return entity;
    }
}