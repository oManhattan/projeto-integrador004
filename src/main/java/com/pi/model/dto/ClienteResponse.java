package com.pi.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pi.model.entity.GeneroPessoa;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(builderMethodName = "clienteResponseBuilder")
@Getter
@Setter
@JsonPropertyOrder({"id", "email", "nome", "sobrenome", "cpf", "dataNascimento", "genero", "altura"})
public class ClienteResponse {
    
    @JsonProperty(namespace = "id")
    private Long id;

    @JsonProperty(namespace = "email")
    private String email;

    @JsonProperty(namespace = "nome")
    private String nome;

    @JsonProperty(namespace = "sobrenome")
    private String sobrenome;

    @JsonProperty(namespace = "cpf")
    private String cpf;

    @JsonProperty(namespace = "dataNascimento")
    private LocalDate dataNascimento;

    @JsonProperty(namespace = "genero")
    private GeneroPessoa genero;
}
