package com.mpayWallet.service;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.BillPaymentDto;
import com.mpayWallet.entity.BillPayment;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BillPaymentService {
    ApiResponse<BillPaymentDto> payBill(BillPaymentDto dto);
    public BillPayment addBillPayment(BillPaymentDto dto);
    List<BillPayment> getBillPayments(Long userId, Optional<String> billType, Optional<Date> startDate, Optional<Date> endDate);
}