package com.mj.exception;

import java.io.Serial;

public class NotEnoughFundsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3461514703587869577L;

    public NotEnoughFundsException(String message) {
        super(message);
    }
}
