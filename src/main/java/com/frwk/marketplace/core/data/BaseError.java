package com.frwk.marketplace.core.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class BaseError {
    
    private String field;

    private String error;
}
