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

import lombok.Data;

@Data
@Entity
@Table(name = "Treino")
public class TreinoEntity {
    
    @ManyToOne(targetEntity = ClienteEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ClienteEntity cliente;

    @OneToMany(targetEntity = ExercicioEntity.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY) 
    private List<ExercicioEntity> exercicios = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private Character titulo;

    @Column(name = "subtitulo")
    private String subtitulo;

}
