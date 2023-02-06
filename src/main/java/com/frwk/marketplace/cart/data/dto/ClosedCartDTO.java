package com.frwk.marketplace.cart.data.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class ClosedCartDTO {
    
    @JsonProperty("idCarrinho")
    private String idCarrinho;

    @JsonProperty("cliente")
    private ClientDTO cliente;

    @JsonProperty("itens")
    private List<ClosedCartItemDTO> itens;
}
