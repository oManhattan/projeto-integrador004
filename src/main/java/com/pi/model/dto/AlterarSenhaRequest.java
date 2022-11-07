package com.pi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonPropertyOrder({"senha_atual", "nova_senha"})
public class AlterarSenhaRequest {
    
    @JsonProperty(namespace = "senha_atual")
    private String senhaAtual;

    @JsonProperty(namespace = "nova_senha")
    private String novaSenha;
}
