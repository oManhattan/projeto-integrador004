package com.pi.model.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.AvaliacaoCorporalEntity;

@Repository
@Transactional
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoCorporalEntity, Long> {
    
    @Query(nativeQuery = true, value = "select * from avaliacao_corporal a where a.cliente_id = :cliente_id order by data_realizacao desc")
    List<AvaliacaoCorporalEntity> encontrarTodasAvaliacoesDoCliente(@Param("cliente_id") Long clienteID);

    @Modifying
    @Query(nativeQuery = true, value = "delete from avaliacao_corporal where id = :id")
    Integer apagarAvaliacao(@Param("id") Long id);
}
