package com.pi.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.ClienteEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Cliente c WHERE c.email = ?1")
    Optional<ClienteEntity> encontrarPorEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM Cliente c WHERE c.cpf = ?1")
    Optional<ClienteEntity> encontrarPorCPF(String cpf);

    @Query(nativeQuery = true, value = "SELECT * FROM Cliente c WHERE c.email = ?1 OR c.cpf = ?2")
    Optional<List<ClienteEntity>> encontrarPorEmailCpf(String email, String cpf);

    @Query(nativeQuery = true, value = "SELECT * FROM Cliente c WHERE c.profissional_id = ?1")
    List<ClienteEntity> todosClientesComProfissional(Long id);
}
