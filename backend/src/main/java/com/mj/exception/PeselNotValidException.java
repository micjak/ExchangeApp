package com.mj.exception;

import java.io.Serial;

public class PeselNotValidException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8928944382849497163L;

    public PeselNotValidException(String message) {
        super(message);
    }
}
