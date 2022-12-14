package com.pi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(builderMethodName = "profissionalResponseBuilder")
@Getter
@Setter
@JsonPropertyOrder({"id", "email", "nome", "sobrenome", "cpf", "cnpj"})
public class ProfissionalResponse {
    
    @JsonProperty(namespace = "id")
    private Long id;

    @JsonProperty(namespace = "nome")
    private String nome;

    @JsonProperty(namespace = "sobrenome")
    private String sobrenome;

    @JsonProperty(namespace = "email")
    private String email;

    @JsonProperty(namespace = "cpf")
    private String cpf;

    @JsonProperty(namespace = "cnpj")
    private String cnpj;
}
