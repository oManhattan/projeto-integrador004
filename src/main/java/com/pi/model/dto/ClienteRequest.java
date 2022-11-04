package com.pi.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pi.model.entity.GeneroPessoa;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder()
@Getter
@Setter
@JsonPropertyOrder({"email", "senha", "nome", "sobrenome", "cpf", "dataNascimento", "genero", "altura"})
public class ClienteRequest {
    
    @JsonProperty(namespace = "email")
    private String email;

    @JsonProperty(namespace = "senha")
    private String senha;

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
