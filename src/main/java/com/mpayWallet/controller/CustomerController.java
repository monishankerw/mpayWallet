package com.mpayWallet.controller;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.CustomerDto;
import com.mpayWallet.dto.CustomerResponseDTO;
import com.mpayWallet.dto.LoginDTO;
import com.mpayWallet.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> register(@Valid @RequestBody CustomerDto dto) {
        log.info("Customer registration request received: {}", dto.getMobile());
        ApiResponse<CustomerResponseDTO> response = customerService.registerCustomer(dto);
        log.info("Customer registered successfully with mobile: {}", dto.getMobile());
        return ResponseEntity.ok(response);
    }
}


