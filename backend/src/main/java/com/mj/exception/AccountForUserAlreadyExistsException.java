package com.mj.exception;

import java.io.Serial;

public class AccountForUserAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -723161012895225303L;

    public AccountForUserAlreadyExistsException(String message) {
        super(message);
    }
}
