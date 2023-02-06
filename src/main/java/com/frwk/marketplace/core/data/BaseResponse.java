package com.frwk.marketplace.core.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class BaseResponse {
    
    private String type;

    private String message;

    @Builder.Default
    private List<BaseError> errors = new ArrayList<>();
}
