package com.frwk.marketplace.cart.data.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder 
@NoArgsConstructor @AllArgsConstructor
public class AddedCartItemDTO {
    
    @JsonProperty("idCarrinho")
    @NotBlank(message = "O campo 'idCarrinho' é obrigatório")
    private String idCarrinho;

    @JsonProperty("idProduto")
    @NotBlank(message = "O campo 'idProduto' é obrigatório")
    private String idProduto;

    @JsonProperty("quantidade")
    @NotNull(message = "O campo 'quantidade' é obrigatório")
    @Min(value = 1, message = "A quantidade informada deve ser maior que 0")
    private Integer quantidade;
}
