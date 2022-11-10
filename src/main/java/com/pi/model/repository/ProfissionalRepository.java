package com.pi.model.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pi.model.entity.ProfissionalEntity;

@Repository
@Transactional
public interface ProfissionalRepository extends JpaRepository<ProfissionalEntity, Long> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM Profissional p WHERE p.email = ?1")
    Optional<ProfissionalEntity> encontrarPorEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM Profissional p WHERE p.cpf = ?1")
    Optional<ProfissionalEntity> encontrarPorCPF(String cpf);

    @Query(nativeQuery = true, value = "SELECT * FROM Profissional p WHERE p.cnpj = ?1")
    Optional<ProfissionalEntity> encontrarPorCNPJ(String cnpj);

    @Query(nativeQuery = true, value = "select if(count(email) > 0, 'true', 'false') from profissional p where p.email = :email")
    Boolean emailExiste(@Param("email") String email);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE Profissional p SET p.senha = ?1 WHERE p.email = ?2")
    Integer alterarSenha(String senha, String email);

    @Modifying
    @Query(nativeQuery = true, value = "update profissional p set p.email = :email, p.nome = :nome, p.sobrenome = :sobrenome, p.cpf = :cpf, p.cnpj = :cnpj where p.id = :id")
    Integer alterarPerfil(@Param("email") String email, @Param("nome") String nome, @Param("sobrenome") String sobrenome, @Param("cpf") String cpf, @Param("cnpj") String cnpj, @Param("id") Long id);

    @Modifying
    @Query(nativeQuery = true, value = "delete from profissional where id = :id")
    Integer apagarPerfil(@Param("id") Long id);
}
