package com.pi.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.TreinoEntity;

@Repository
public interface TreinoRepository extends JpaRepository<TreinoEntity, Long> {
    
    @Query(nativeQuery = true, value = "select * from treino t where cliente_id = :id")
    List<TreinoEntity> treinosDoCliente(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select * from treino where id = :treino_id and cliente_id = :cliente_id")
    Optional<TreinoEntity> encontrarTreinoPorIdComCliente(@Param("treino_id") Long id, @Param("cliente_id") Long clienteID);

    @Modifying
    @Query(nativeQuery = true, value = "update treino t set t.titulo = :titulo, t.subtitulo = :subtitulo where t.id = :id")
    Integer atualizarTreino(@Param("titulo") Character titulo, @Param("subtitulo") String subtitulo, @Param("id") Long id);

    @Modifying
    @Query(nativeQuery = true, value = "delete treino t where t.id = :id)")
    Integer apagarTreino(@Param("id") Long id);
}
