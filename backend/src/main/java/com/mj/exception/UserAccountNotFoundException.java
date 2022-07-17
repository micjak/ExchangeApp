package com.mj.exception;

import java.io.Serial;

public class UserAccountNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7447300725482600122L;

    public UserAccountNotFoundException(String message) {
        super(message);
    }
}
