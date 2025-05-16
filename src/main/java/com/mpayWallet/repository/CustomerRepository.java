package com.mpayWallet.repository;

import com.mpayWallet.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByMobile(String mobile);
    Optional<Customer> findByMobile(String mobile);
}
