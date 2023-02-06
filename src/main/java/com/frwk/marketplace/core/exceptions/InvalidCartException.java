package com.frwk.marketplace.core.exceptions;

public class InvalidCartException extends BaseException {

    public InvalidCartException(String message) {
        super("InvalidCartException", message);
    }
    
}
