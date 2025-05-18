package com.mpayWallet.controller;

import com.mpayWallet.dto.ApiResponse;
import com.mpayWallet.dto.BillPaymentDto;
import com.mpayWallet.entity.BillPayment;
import com.mpayWallet.service.BillPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bill-payments")
@RequiredArgsConstructor
public class BillPaymentController {

    private final BillPaymentService billPaymentService;

    @PostMapping("/pay")
    public ResponseEntity<ApiResponse<BillPaymentDto>> payBill(@Valid @RequestBody BillPaymentDto billPaymentDto) {
        return ResponseEntity.ok(billPaymentService.payBill(billPaymentDto));
    }
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<BillPayment>> getPaymentHistory(
            @PathVariable Long userId,
            @RequestParam Optional<String> billType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> endDate
    ) {
        List<BillPayment> payments = billPaymentService.getBillPayments(userId, billType, startDate, endDate);
        return ResponseEntity.ok(payments);
    }
}