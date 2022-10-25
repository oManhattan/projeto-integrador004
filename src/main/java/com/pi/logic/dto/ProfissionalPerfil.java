package com.pi.logic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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

    public ProfissionalPerfil() {

    }

    public ProfissionalPerfil(String nome, String sobrenome, String email, String cpf, String cnpj) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.cpf = cpf;
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    
}
