package com.mpayWallet.repository;

import com.mpayWallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTransactionType(String type);
    List<Transaction> findByTransactionDateBetween(Timestamp startDate, Timestamp endDate);
}