package com.mpayWallet.repository;


import com.mpayWallet.entity.BeneficiaryDetails;
import com.mpayWallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeneficiaryRepository extends JpaRepository<BeneficiaryDetails, Long> {
    List<BeneficiaryDetails> findByWallet(Wallet wallet);
    boolean existsByWalletAndMobile(Wallet wallet, Long mobile);
}