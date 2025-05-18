package com.mpayWallet.service.impl;

import com.mpayWallet.dto.KycDetailsDto;
import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.entity.KycDetails;
import com.mpayWallet.repository.CustomerRepository;
import com.mpayWallet.repository.KycDetailsRepository;
import com.mpayWallet.service.KycService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class KycServiceImpl implements KycService {


    private final KycDetailsRepository kycRepo;
    private final CustomerRepository customerRepo; // ✅ Add this

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ApiResponse<String> submitKyc(KycDetailsDto dto, MultipartFile file) {
        try {
            // ✅ Check if customer exists
            if (!customerRepo.existsBycustomerId(dto.getCustomerId())) {
                return new ApiResponse<>(false, "Customer with ID " + dto.getCustomerId() + " does not exist", null);
            }

            // ✅ Check if PAN already exists
            if (kycRepo.existsByPanNumber(dto.getPanNumber())) {
                return new ApiResponse<>(false, "PAN number already registered", null);
            }

            // ✅ Check if Aadhar already exists
            if (kycRepo.existsByAadharNumber(dto.getAadharNumber())) {
                return new ApiResponse<>(false, "Aadhar number already registered", null);
            }

            // ✅ Save document to file system
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + File.separator + filename);
            Files.write(path, file.getBytes());

            // ✅ Build and save KYC
            KycDetails kyc = KycDetails.builder()
                    .customerId(dto.getCustomerId())
                    .panNumber(dto.getPanNumber())
                    .aadharNumber(dto.getAadharNumber())
                    .address(dto.getAddress())
                    .documentType(dto.getDocumentType())
                    .documentUrl(path.toString())
                    .status("PENDING")
                    .submittedDate(new Date())
                    .build();

            kycRepo.save(kyc);
            return new ApiResponse<>(true, "KYC submitted successfully", null);

        } catch (IOException e) {
            return new ApiResponse<>(false, "File upload failed", null);
        }
    }

    @Override
    public List<KycDetailsDto> getAllKyc() {
        log.info("Fetching all KYC records");
        List<KycDetailsDto> list = kycRepo.findAll().stream().map(k -> KycDetailsDto.builder()
                .kycId(k.getKycId())
                .customerId(k.getCustomerId())
                .panNumber(k.getPanNumber())
                .aadharNumber(k.getAadharNumber())
                .address(k.getAddress())
                .documentType(k.getDocumentType())
                .documentUrl(k.getDocumentUrl())
                .status(k.getStatus())
                .submittedDate(k.getSubmittedDate())
                .build()).collect(Collectors.toList());
        log.info("Total KYC records fetched: {}", list.size());
        return list;
    }

    @Override
    public ApiResponse<KycDetailsDto> getKycByUserId(Long customerId) {
        log.info("Fetching KYC for customerId: {}", customerId);
        Optional<KycDetails> kycOpt = kycRepo.findByCustomerId(customerId);
        if (kycOpt.isPresent()) {
            KycDetails k = kycOpt.get();
            KycDetailsDto dto = KycDetailsDto.builder()
                    .kycId(k.getKycId())
                    .customerId(k.getCustomerId())
                    .panNumber(k.getPanNumber())
                    .aadharNumber(k.getAadharNumber())
                    .address(k.getAddress())
                    .documentType(k.getDocumentType())
                    .documentUrl(k.getDocumentUrl())
                    .status(k.getStatus())
                    .submittedDate(k.getSubmittedDate())
                    .build();
            log.info("KYC found for userId: {}", customerId);
            return new ApiResponse<>(true, "KYC found", dto);
        }
        log.warn("KYC not found for userId: {}", customerId);
        return new ApiResponse<>(false, "KYC not found", null);
    }

    @Override
    public ApiResponse<String> updateKycStatus(Long kycId, String status) {
        log.info("Updating KYC status for kycId: {} to {}", kycId, status);
        Optional<KycDetails> kycOpt = kycRepo.findById(kycId);
        if (kycOpt.isPresent()) {
            KycDetails k = kycOpt.get();
            k.setStatus(status.toUpperCase());
            kycRepo.save(k);
            log.info("KYC status updated successfully for kycId: {}", kycId);
            return new ApiResponse<>(true, "KYC status updated to " + status, null);
        }
        log.warn("KYC not found for kycId: {}", kycId);
        return new ApiResponse<>(false, "KYC not found", null);
    }
}