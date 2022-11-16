package com.pi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.AvaliacaoCorporalEntity;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoCorporalEntity, Long> {
    
    @Query(nativeQuery = true, value = "select * from avaliacao_corporal a where a.cliente_id = :cliente_id")
    List<AvaliacaoCorporalEntity> encontrarTodasAvaliacoesDoCliente(@Param("cliente_id") Long clienteID);

}
