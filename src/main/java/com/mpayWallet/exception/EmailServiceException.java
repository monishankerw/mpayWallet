package com.mpayWallet.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailServiceException(String message) {
        super(message);
        log.error("Email Service Error: {}", message);
    }

    public EmailServiceException(String message, Throwable cause) {
        super(message, cause);
        log.error("Email Service Error: {} - Cause: {}", message, cause.getMessage());
        log.debug("Stack trace:", cause);
    }
}