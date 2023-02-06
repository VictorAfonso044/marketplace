package com.frwk.marketplace.core.exceptions;

public class InvalidClientException extends BaseException {
    
    public InvalidClientException(String message) {
        super("InvalidClientException", message);
    }
}
