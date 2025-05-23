package com.mpayWallet.exception;

public class SmsServiceException extends RuntimeException {
    public SmsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

