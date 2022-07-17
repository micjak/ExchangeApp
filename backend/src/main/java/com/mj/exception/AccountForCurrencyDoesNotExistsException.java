package com.mj.exception;

import java.io.Serial;

public class AccountForCurrencyDoesNotExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7906966826722084132L;

    public AccountForCurrencyDoesNotExistsException(String message) {
        super(message);
    }
}
