package com.frwk.marketplace.core.exceptions;

public class InvalidProductException extends BaseException {
    
    public InvalidProductException(String message) {
        super("InvalidProductException", message);
    }
}
