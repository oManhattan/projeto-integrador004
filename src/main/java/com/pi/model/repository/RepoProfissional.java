package com.pi.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.Profissional;

@Repository
public interface RepoProfissional extends JpaRepository<Profissional, Long> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM Profissional p WHERE p.email = ?1")
    Optional<Profissional> emailExiste(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM Profissional p WHERE p.cpf = ?1")
    Optional<Profissional> cpfExiste(String cpf);

    @Query(nativeQuery = true, value = "SELECT * FROM Profissional p WHERE p.cnpj = ?1")
    Optional<Profissional> cnpjExiste(String cnpj);

}
