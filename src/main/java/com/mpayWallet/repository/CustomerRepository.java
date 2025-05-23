package com.mpayWallet.repository;

import com.mpayWallet.entity.Customer;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByMobile(Long mobile);
    Optional<Customer> findByMobile(Long mobile);
    String getKycStatus(Long customerId);

    boolean existsBycustomerId(Long customerId);

    boolean existsByEmail(String email);
}
