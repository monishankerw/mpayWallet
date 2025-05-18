package com.mpayWallet.service;

import com.mpayWallet.dto.*;

public interface WalletService {
    ApiResponse<WalletDto> getWalletByCustomerId(Long customerId);
    ApiResponse<BankToWalletResponse> transferFromBank(Long walletId, BankToWalletRequest dto);
    ApiResponse<AddMoneyResponse> addMoneyByMobile(AddMoneyRequest request);

}