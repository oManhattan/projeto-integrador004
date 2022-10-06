package com.pi.model.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AvaliacaoCorporal")
public class AvaliacaoCorporal {
    
    private Integer id;

    private LocalDate dataRealizacao;

    private Float peso;

    private Float musculoEsqueletico;

    private Integer idadeCorporal;

    private Float gorduraCorporal;

    private Integer gorduraVisceral;

    private Integer metabolismoBasal;

    
}
