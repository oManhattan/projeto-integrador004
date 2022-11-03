package com.pi.model.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.ProfissionalEntity;

@Repository
public interface ProfissionalRepository extends JpaRepository<ProfissionalEntity, Long> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM Profissional p WHERE p.email = ?1")
    Optional<ProfissionalEntity> encontrarPorEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM Profissional p WHERE p.cpf = ?1")
    Optional<ProfissionalEntity> encontrarPorCPF(String cpf);

    @Query(nativeQuery = true, value = "SELECT * FROM Profissional p WHERE p.cnpj = ?1")
    Optional<ProfissionalEntity> encontrarPorCNPJ(String cnpj);

}
