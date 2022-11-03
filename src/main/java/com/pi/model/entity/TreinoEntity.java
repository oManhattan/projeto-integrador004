package com.pi.model.entity;

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
@Table(name = "Treino")
public class TreinoEntity {
    
    @ManyToOne(targetEntity = ClienteEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ClienteEntity cliente;

    @OneToMany(targetEntity = ExercicioEntity.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY) 
    private List<ExercicioEntity> exercicios;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private Character titulo;

    @Column(name = "subtitulo")
    private String subtitulo;

}
