package com.frwk.marketplace.cart.data.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ClientDTO {
    
    @JsonProperty("nome")
    @NotBlank(message = "O campo 'nome' é obrigatório")
    private String nome;

    @JsonProperty("cpf")
    @NotBlank(message = "O campo 'cpf' é obrigatório")
    @Pattern(regexp = "([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}[/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}[-]?[0-9]{2})", message = "CPF inválido")
    private String cpf;

    @JsonProperty("dataNascimento")
    @NotBlank(message = "O campo 'dataNascimento' é obrigatório")
    @Pattern(regexp = "^([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([0]?[1-9]|[1][0-2])[./-]([0-9]{4}|[0-9]{2})$", message = "Informe a data no formato dd/MM/yyyy")
    private String dataNascimento;

    @JsonProperty("email")
    @NotBlank(message = "O campo 'email' é obrigatório")
    @Email(message = "Email inválido")
    private String email;
}
