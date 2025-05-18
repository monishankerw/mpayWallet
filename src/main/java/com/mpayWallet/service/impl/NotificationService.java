package com.mpayWallet.service.impl;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notifyUser(Long userId, String message) {
        // Here you would integrate real SMS/email APIs.

        // For demo, print to console:
        System.out.println("Notify user " + userId + ": " + message);
    }
}