package com.mpayWallet.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String insufficientBankBalance) {
        super("Insufficient balance in bank account");
    }
}