//package com.mpayWallet.service.impl;
//
//import com.mpayWallet.dto.ApiResponse;
//import com.mpayWallet.dto.BillPaymentDto;
//import com.mpayWallet.entity.BillPayment;
//import com.mpayWallet.entity.Transaction;
//import com.mpayWallet.entity.Wallet;
//import com.mpayWallet.exception.ResourceNotFoundException;
//import com.mpayWallet.repository.BillPaymentRepository;
//import com.mpayWallet.repository.TransactionRepository;
//import com.mpayWallet.repository.WalletRepository;
//import com.mpayWallet.service.BillPaymentService;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class BillPaymentServiceImpl implements BillPaymentService {
//
//    private final WalletRepository walletRepository;
//    private final BillPaymentRepository billPaymentRepository;
//    private final TransactionRepository transactionRepository;
//    private final NotificationService notificationService;
//
//    @Transactional
//    @Override
//    public ApiResponse<BillPaymentDto> payBill(BillPaymentDto dto) {
//        Wallet wallet = walletRepository.findById(dto.getWalletId())
//                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with id: " + dto.getWalletId()));
//
//        if (wallet.getBalance() < dto.getAmount()) {
//            throw new RuntimeException("Insufficient wallet balance");
//        }
//
//        // Deduct balance
//        wallet.setBalance(wallet.getBalance() - dto.getAmount());
//        walletRepository.save(wallet);
//
//        // Save Bill Payment
//        BillPayment billPayment = BillPayment.builder()
//                .wallet(wallet)
//                .userId(dto.getUserId())
//                .billType(dto.getBillType())
//                .amount(dto.getAmount())
//                .paymentDate(new Date())
//                .build();
//        billPaymentRepository.save(billPayment);
//
//        // Create DEBIT transaction
//        Transaction txn = Transaction.builder()
//                .wallet(wallet)
//                .transactionType("DEBIT")
//                .amount(dto.getAmount())
//                .transactionDate(new Date())
//                .transactionDesc("Bill Payment for " + dto.getBillType())
//                .build();
//        transactionRepository.save(txn);
//
//        // Prepare response
//        BillPaymentDto responseDto = BillPaymentDto.builder()
//                .billId(billPayment.getBillId())
//                .walletId(wallet.getWalletId())
//                .userId(billPayment.getUserId())
//                .billType(billPayment.getBillType())
//                .amount(billPayment.getAmount())
//                .paymentDate(billPayment.getPaymentDate())
//                .build();
//
//        return new ApiResponse<>(true, "Bill paid successfully", responseDto);
//    }
//
//    @Override
//    public BillPayment addBillPayment(BillPaymentDto dto) {
//        Wallet wallet = walletRepository.findById(dto.getWalletId())
//                .orElseThrow(() -> new RuntimeException("Wallet not found"));
//
//        BillPayment payment = BillPayment.builder()
//                .wallet(wallet)
//                .userId(dto.getUserId())
//                .billType(dto.getBillType())
//                .amount(dto.getAmount())
//                .paymentDate(dto.getPaymentDate() != null ? dto.getPaymentDate() : new Date())
//                .build();
//
//        BillPayment savedPayment = billPaymentRepository.save(payment);
//
//        // Notify user via SMS/email
//        notificationService.notifyUser(dto.getUserId(),
//                "Bill Payment of " + dto.getAmount() + " for " + dto.getBillType() + " was successful.");
//
//        return savedPayment;
//    }
//
//    @Override
//    public List<BillPayment> getBillPayments(Long userId, Optional<String> billType, Optional<Date> startDate, Optional<Date> endDate) {
//        if (billType.isPresent() && startDate.isPresent() && endDate.isPresent()) {
//            return billPaymentRepository.findByUserIdAndBillTypeAndPaymentDateBetween(userId, billType.get(), startDate.get(), endDate.get());
//        } else if (billType.isPresent()) {
//            return billPaymentRepository.findByUserIdAndBillType(userId, billType.get());
//        } else if (startDate.isPresent() && endDate.isPresent()) {
//            return billPaymentRepository.findByUserIdAndPaymentDateBetween(userId, startDate.get(), endDate.get());
//        } else {
//            return billPaymentRepository.findByUserId(userId);
//        }
//    }
//}