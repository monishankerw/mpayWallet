package com.mpayWallet.service.impl;

import com.mpayWallet.entity.Customer;
import com.mpayWallet.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String mobileStr) throws UsernameNotFoundException {
        try {
            Long mobile = Long.parseLong(mobileStr); // ✅ Convert input string to long

            Customer customer = customerRepository.findByMobile(mobile)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with mobile: " + mobileStr));

            return new org.springframework.security.core.userdetails.User(
                    customer.getMobile().toString(), // Spring needs username as String
                    customer.getPassword(),
                    new ArrayList<>()
            );

        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid mobile number format: " + mobileStr);
        }
    }
}