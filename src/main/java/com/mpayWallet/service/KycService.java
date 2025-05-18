package com.mpayWallet.service;

import com.mpayWallet.dto.KycDetailsDto;
import com.mpayWallet.dto.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface KycService {
    ApiResponse<String> submitKyc(KycDetailsDto dto, MultipartFile file);
    List<KycDetailsDto> getAllKyc();
    ApiResponse<KycDetailsDto> getKycByUserId(Long customerId);
    ApiResponse<String> updateKycStatus(Long kycId, String status);
}