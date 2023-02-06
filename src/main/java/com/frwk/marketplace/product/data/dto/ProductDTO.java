package com.frwk.marketplace.product.data.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class ProductDTO {
    
    @JsonProperty("id")
    private UUID id;
    
    @JsonProperty("nome")
    @NotBlank(message = "O campo 'nome' é obrigatório")
    @Size(min = 1, max = 200, message = "O campo 'nome' precisa ter 1 a 200 caracteres")
    private String nome;
    
    @JsonProperty("preco")
    @NotNull(message = "O campo 'preço' é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "O campo 'preço' inválido")
    private Double preco;
}
