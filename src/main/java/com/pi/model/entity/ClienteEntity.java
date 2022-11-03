package com.pi.model.entity;

import java.time.LocalDate;
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
@Table(name = "Cliente")
public class ClienteEntity {
    
    @OneToMany(targetEntity = TreinoEntity.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<TreinoEntity> treinos;

    @ManyToOne(targetEntity = ProfissionalEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProfissionalEntity profissional;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false, unique = false)
    private String senha;
    
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

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

}
