package com.pi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.ExercicioEntity;

@Repository
public interface ExercicioRepository extends JpaRepository<ExercicioEntity, Long> {
    
    @Query(nativeQuery = true, value = "select * from exercicio where treino_id = :treino_id")
    List<ExercicioEntity> encontrarTodosComTreinoID(@Param("treino_id") Long treinoID);

}
