package com.mpayWallet.service;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.BankToWalletRequest;
import com.mpayWallet.dto.BankToWalletResponse;
import com.mpayWallet.dto.WalletDto;

public interface WalletService {
    ApiResponse<WalletDto> getWalletByCustomerId(Long customerId);
    ApiResponse<BankToWalletResponse> transferFromBank(Long walletId, BankToWalletRequest dto);

}