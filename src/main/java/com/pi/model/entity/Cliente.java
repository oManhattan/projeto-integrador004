package com.pi.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Cliente")
public class Cliente {
    
    @OneToMany(targetEntity = Treino.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Treino> treinos = new ArrayList<>();

    @ManyToOne(targetEntity = Profissional.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profissional profissional;

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

    public Cliente() {
        
    }

    public Cliente(Integer id, String nome, String sobrenome, LocalDate dataNascimento, GeneroPessoa genero,
            Float altura, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.altura = altura;
        this.email = email;
        this.senha = senha;
    }
}
