package com.mpayWallet.exception;

public class DuplicateKycException extends RuntimeException {
    public DuplicateKycException(String message) { super(message); }
}
