package com.pi.model.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pi.model.entity.ClienteEntity;

@Repository
@Transactional
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Cliente c WHERE c.email = ?1")
    Optional<ClienteEntity> encontrarPorEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM Cliente c WHERE c.cpf = ?1")
    Optional<ClienteEntity> encontrarPorCPF(String cpf);

    @Query(nativeQuery = true, value = "SELECT * FROM Cliente c WHERE c.id = ?1")
    Optional<ClienteEntity> encontrarPorId(Long id);

    @Query(nativeQuery = true, value = "select if(count(email) > 0, 'true', 'false') from cliente c where c.email = :email")
    Boolean emailExiste(@Param("email") String email);

    @Query(nativeQuery = true, value = "select if(count(cpf) > 0, 'true', 'false') from cliente c where c.cpf = :cpf")
    Boolean cpfExiste(@Param("cpf") String cpf);

    @Query(nativeQuery = true, value = "SELECT * FROM Cliente c WHERE c.profissional_id = ?1")
    List<ClienteEntity> todosClientesComProfissional(Long id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE Cliente c SET c.senha = ?1 WHERE c.email = ?2")
    Integer alterarSenha(String senha, String email);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE Cliente c SET c.email = :email, c.nome = :nome, c.sobrenome = :sobrenome, genero = :genero, c.data_nascimento = :dataNascimento WHERE c.id = :id")
    Integer alterarPerfil(@Param("email") String email, @Param("nome") String nome, @Param("sobrenome") String sobrenome, @Param("genero") String genero, @Param("dataNascimento") LocalDate dataNascimento, @Param("id") Long id);

    @Modifying
    @Query(nativeQuery = true, value = "delete from cliente c where c.id = :id")
    Integer deletarPerfil(@Param("id") Long id);
}
