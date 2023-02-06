package com.frwk.marketplace.core.exceptions;

import com.frwk.marketplace.core.shared.constants.AppConstants;

import lombok.Getter;

@Getter
public class InvalidDateException extends BaseException {
 
    private final String field;

    public InvalidDateException(String field, String message) {
        super(AppConstants.VALIDATION_ERROR, message);
        this.field = field;
    }
}
