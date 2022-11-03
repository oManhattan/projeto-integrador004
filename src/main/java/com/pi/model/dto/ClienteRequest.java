package com.pi.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pi.model.entity.GeneroPessoa;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"id", "email", "senha", "nome", "sobrenome", "cpf", "dataNascimento", "genero", "altura"})
public class ClienteRequest extends ClienteResponse {
    
    @JsonProperty(namespace = "senha")
    private String senha;

    @Builder(builderMethodName = "clienteRequestBuilder")
    public ClienteRequest(Long id, String email, String nome, String sobrenome, String cpf, LocalDate dataNascimento,
            GeneroPessoa genero, Float altura, String senha) {
        super(id, email, nome, sobrenome, cpf, dataNascimento, genero, altura);
        this.senha = senha;
    }

}
