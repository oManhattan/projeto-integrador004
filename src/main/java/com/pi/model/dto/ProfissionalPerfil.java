package com.pi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"nome", "sobrenome", "email", "cpf", "cnpj"})
public class ProfissionalPerfil {
    
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
