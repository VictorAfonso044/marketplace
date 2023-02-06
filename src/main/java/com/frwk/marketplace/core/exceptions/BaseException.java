package com.frwk.marketplace.core.exceptions;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public final String type;

    public BaseException(String type, String message) {
        super(message);
        this.type = type;
    }
}
