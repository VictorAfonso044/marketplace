package com.frwk.marketplace.cart.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class CreatedCartDTO {
    
    @JsonProperty("idCarrinho")
    private String idCarrinho;

    @JsonIgnore
    private transient boolean isNew;
}
