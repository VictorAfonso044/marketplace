package com.frwk.marketplace.cart.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.frwk.marketplace.product.data.dto.ProductDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class ClosedCartItemDTO {
    
    @JsonProperty("produto")
    private ProductDTO produto;

    @JsonProperty("quantidade")
    private Integer quantidade;
}
