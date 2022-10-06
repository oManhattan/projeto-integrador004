package com.pi.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Cliente")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "nome", nullable = false, unique = false)
    private String nome;

    @Column(name = "sobrenome", nullable = false, unique = false)
    private String sobrenome;

    @Column(name = "data_nascimento", nullable = false, unique = false)
    private LocalDate dataNascimento;

    @Column(name = "genero", nullable = false, unique = false)
    private GeneroPessoa genero;

    @Column(name = "altura", nullable = false, unique = false)
    private Float altura;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false, unique = false)
    private String senha;


}
