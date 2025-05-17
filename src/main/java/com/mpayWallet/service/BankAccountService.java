package com.mpayWallet.service;

import com.mpayWallet.dto.BankAccountDto;
import com.mpayWallet.dto.ApiResponse;

public interface BankAccountService {
    ApiResponse<BankAccountDto> addBankAccount(BankAccountDto bankAccountDto);
    ApiResponse<BankAccountDto> getBankAccountByAccountNumber(Long accountNumber);
}