package com.pi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(builderMethodName = "profissionalRequestBuilder")
@Getter
@Setter
@JsonPropertyOrder({"email", "senha", "nome", "sobrenome", "cpf", "cnpj"})
public class ProfissionalRequest {
    
    @JsonProperty(namespace = "email")
    private String email;

    @JsonProperty(namespace = "senha", required = false)
    private String senha;

    @JsonProperty(namespace = "nome")
    private String nome;

    @JsonProperty(namespace = "sobrenome")
    private String sobrenome;

    @JsonProperty(namespace = "cpf")
    private String cpf;
    
    @JsonProperty(namespace = "cnpj")
    private String cnpj;
}
