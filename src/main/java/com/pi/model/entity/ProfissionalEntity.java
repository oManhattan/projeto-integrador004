package com.pi.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "Profissional")
public class ProfissionalEntity implements UserAccount {

    @OneToMany(targetEntity = ClienteEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<ClienteEntity> clientes;

    @Transient
    private final UserAuthority authority = UserAuthority.PROFISSIONAL;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "nome", nullable = false, unique = false)
    private String nome;

    @Column(name = "sobrenome", nullable = false, unique = false)
    private String sobrenome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "cnpj", nullable = true, unique = true)
    private String cnpj;

    @Column(name = "senha", nullable = false, unique = false)
    private String senha;

}
