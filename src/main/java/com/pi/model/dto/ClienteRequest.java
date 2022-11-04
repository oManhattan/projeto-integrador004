package com.pi.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pi.model.entity.GeneroPessoa;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder()
@Getter
@Setter
@ToString
@JsonPropertyOrder({"email", "nome", "sobrenome", "cpf", "dataNascimento", "genero"})
public class ClienteRequest {
    
    @JsonProperty(namespace = "email")
    private String email;

    @JsonIgnore
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
