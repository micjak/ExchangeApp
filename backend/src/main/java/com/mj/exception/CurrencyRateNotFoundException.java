package com.mj.exception;

import java.io.Serial;

public class CurrencyRateNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3662555624353039524L;

    public CurrencyRateNotFoundException(String message) {
        super(message);
    }
}
