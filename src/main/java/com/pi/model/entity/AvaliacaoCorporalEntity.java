package com.pi.model.entity;

import java.time.LocalDate;

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
@Table(name = "AvaliacaoCorporal")
public class AvaliacaoCorporalEntity {
    
    @ManyToOne(targetEntity = ClienteEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private ClienteEntity cliente;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data_realizacao")
    private LocalDate dataRealizacao;

    @Column(name = "altura")
    private Integer altura;

    @Column(name = "peso")
    private Float peso;
    
    @Column(name = "musculo_esqueletico")
    private Float musculoEsqueletico;

    @Column(name = "idade_corporal")
    private Integer idadeCorporal;

    @Column(name = "gordura_corporal")
    private Float gorduraCorporal;

    @Column(name = "gordura_visceral")
    private Integer gorduraVisceral;

    @Column(name = "metabolismo_basal")
    private Integer metabolismoBasal;

    // Medidas do tronco

    @Column(name = "torax")
    private Integer torax;

    @Column(name = "cintura")
    private Integer cintura;
    
    @Column(name = "abdome")
    private Integer abdome;

    @Column(name = "quadril")
    private Integer quadril;

    // Medidas membros superiores

    @Column(name = "biceps_esquerdo")
    private Integer bicepsEsquerdo;

    @Column(name = "antebraco_esquerdo")
    private Integer antebracoEsquerdo;
    
    @Column(name = "biceps_direito")
    private Integer bicepsDireito;
    
    @Column(name = "antebraco_direito")
    private Integer antebracoDireito;

    // Medidas membros inferiores

    @Column(name = "coxa_esquerda")
    private Integer coxaEsquerda;
    
    @Column(name = "panturrilha_esquerda")
    private Integer panturrilhaEsquerda;
    
    @Column(name = "coxa_direita")
    private Integer coxaDireita;
    
    @Column(name = "panturrilha_direita")
    private Integer panturrilhaDireita;

}
