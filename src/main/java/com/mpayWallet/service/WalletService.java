package com.mpayWallet.service;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.WalletDto;

public interface WalletService {
    ApiResponse<WalletDto> getWalletByCustomerId(Long customerId);
}