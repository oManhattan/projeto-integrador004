package com.pi.model.entity;

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
@Table(name = "Exercicio")
public class ExercicioEntity {
    
    @ManyToOne(targetEntity = TreinoEntity.class, fetch = FetchType.EAGER)
    private TreinoEntity treino;

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
