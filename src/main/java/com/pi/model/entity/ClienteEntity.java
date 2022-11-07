package com.pi.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "Cliente")
public class ClienteEntity implements UserAccount {
    
    @OneToMany(targetEntity = TreinoEntity.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<TreinoEntity> treinos;

    @ManyToOne(targetEntity = ProfissionalEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProfissionalEntity profissional;

    @Transient
    private final UserAuthority authority = UserAuthority.CLIENTE;

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

    @Column(name = "sobrenome", nullable = true, unique = false)
    private String sobrenome;

    @Column(name = "data_nascimento", nullable = false, unique = false)
    private LocalDate dataNascimento;

    @Column(name = "genero", nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private GeneroPessoa genero;

}
