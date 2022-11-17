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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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

    @Column(name = "peso", scale = 2)
    private Float peso;
    
    @Column(name = "musculo_esqueletico", scale = 2)
    private Float musculoEsqueletico;//via bioimpedancia

    @Column(name = "idade_corporal")
    private Integer idadeCorporal;//via bioimpedancia

    @Column(name = "gordura_corporal", scale = 2)
    private Float gorduraCorporal;

    @Column(name = "gordura_visceral", scale = 2)
    private Float gorduraVisceral;

    @Column(name = "metabolismo_basal")
    private Integer metabolismoBasal;//pesquisar calculo

    // Medidas do tronco

    @Column(name = "torax", scale = 2)
    private Float torax;

    @Column(name = "cintura", scale = 2)
    private Float cintura;
    
    @Column(name = "abdome", scale = 2)
    private Float abdome;

    @Column(name = "quadril", scale = 2)
    private Float quadril;

    // Medidas membros superiores

    @Column(name = "biceps_esquerdo", scale = 2)
    private Float bicepsEsquerdo;

    @Column(name = "antebraco_esquerdo", scale = 2)
    private Float antebracoEsquerdo;
    
    @Column(name = "biceps_direito", scale = 2)
    private Float bicepsDireito;
    
    @Column(name = "antebraco_direito", scale = 2)
    private Float antebracoDireito;

    // Medidas membros inferiores

    @Column(name = "coxa_esquerda", scale = 2)
    private Float coxaEsquerda;
    
    @Column(name = "panturrilha_esquerda", scale = 2)
    private Float panturrilhaEsquerda;
    
    @Column(name = "coxa_direita", scale = 2)
    private Float coxaDireita;
    
    @Column(name = "panturrilha_direita", scale = 2)
    private Float panturrilhaDireita;

}
