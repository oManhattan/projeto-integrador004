package com.pi.model.entity;

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
@Table(name = "Treino")
public class Treino {
    
    @ManyToOne(targetEntity = Cliente.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cliente cliente;

    @OneToMany(targetEntity = Exercicio.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY) 
    private List<Exercicio> exercicios = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titulo")
    private Character titulo;

    @Column(name = "subtitulo")
    private String subtitulo;

    public Treino() {

    }
}
