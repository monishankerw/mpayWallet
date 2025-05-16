package com.mpayWallet.service;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.CustomerDto;
import com.mpayWallet.dto.CustomerResponseDTO;
import com.mpayWallet.dto.LoginDTO;

public interface CustomerService {
    ApiResponse<CustomerResponseDTO> registerCustomer(CustomerDto dto);
}
