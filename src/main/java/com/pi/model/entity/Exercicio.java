package com.pi.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Exercicio")
public class Exercicio {
    
    @ManyToOne(targetEntity = Treino.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Treino treino;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "musculo")
    private String musculo;

    @Column(name = "serie")
    private Integer serie;

    @Column(name = "repeticao")
    private Integer repeticao;
    
}
