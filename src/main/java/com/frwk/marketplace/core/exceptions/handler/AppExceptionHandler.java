package com.frwk.marketplace.core.exceptions.handler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.frwk.marketplace.core.data.BaseError;
import com.frwk.marketplace.core.data.BaseResponse;
import com.frwk.marketplace.core.exceptions.BaseException;
import com.frwk.marketplace.core.exceptions.EntityNotFoundException;
import com.frwk.marketplace.core.exceptions.InvalidCartException;
import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.core.exceptions.InvalidDateException;
import com.frwk.marketplace.core.exceptions.InvalidProductException;
import com.frwk.marketplace.core.shared.constants.AppConstants;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<BaseError> errors = ex.getBindingResult().getAllErrors().stream()
                .map(err -> this.parseFieldErrorToBaseError((FieldError) err))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(BaseResponse.builder().type(AppConstants.VALIDATION_ERROR)
                .message(AppConstants.VALIDATION_ERROR_MESSAGE).errors(errors).build());
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<BaseResponse> handleValidationExceptions(InvalidDateException ex) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(BaseResponse.builder().type(ex.getType()).message(AppConstants.VALIDATION_ERROR_MESSAGE)
                .errors(Collections.singletonList(BaseError.builder().field(ex.getField()).error(ex.getMessage()).build())).build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseResponse> handleValidationExceptions(EntityNotFoundException ex) {
        return this.handleBaseException(ex);
    }
    
    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<BaseResponse> handleValidationExceptions(InvalidProductException ex) {
        return this.handleBaseException(ex);
    }

    @ExceptionHandler(InvalidClientException.class)
    public ResponseEntity<BaseResponse> handleValidationExceptions(InvalidClientException ex) {
        return this.handleBaseException(ex);
    }

    @ExceptionHandler(InvalidCartException.class)
    public ResponseEntity<BaseResponse> handleValidationExceptions(InvalidCartException ex) {
        return this.handleBaseException(ex);
    }

    private ResponseEntity<BaseResponse> handleBaseException(BaseException ex) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
            .body(BaseResponse.builder().type(ex.getType()).message(ex.getMessage()).build());
    }
    
    private BaseError parseFieldErrorToBaseError(FieldError error) {
        return BaseError.builder().field(error.getField()).error(error.getDefaultMessage()).build();
    }
}
