package com.mpayWallet.repository;

import com.mpayWallet.entity.KycDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KycDetailsRepository extends JpaRepository<KycDetails, Long> {
    Optional<KycDetails> findByCustomerId(Long customerId);
    boolean existsByPanNumber(String panNumber);
    boolean existsByAadharNumber(String aadharNumber);
}