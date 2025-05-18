package com.mpayWallet.service;
import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.BeneficiaryDetailsDto;
import com.mpayWallet.dto.BeneficiaryResponse;

import java.util.List;

public interface BeneficiaryService {
    ApiResponse<BeneficiaryResponse> addBeneficiary(BeneficiaryDetailsDto request);
    ApiResponse<List<BeneficiaryDetailsDto>> getAllBeneficiaries(Long walletId);
}