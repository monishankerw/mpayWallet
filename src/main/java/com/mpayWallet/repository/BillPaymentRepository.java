package com.mpayWallet.repository;

import com.mpayWallet.entity.BillPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BillPaymentRepository extends JpaRepository<BillPayment, Long> {
    List<BillPayment> findByUserId(Long userId);

    List<BillPayment> findByUserIdAndBillType(Long userId, String billType);

    List<BillPayment> findByUserIdAndPaymentDateBetween(Long userId, Date startDate, Date endDate);

    List<BillPayment> findByUserIdAndBillTypeAndPaymentDateBetween(Long userId, String billType, Date startDate, Date endDate);

}