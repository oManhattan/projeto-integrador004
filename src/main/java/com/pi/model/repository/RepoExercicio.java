package com.pi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.ExercicioEntity;

@Repository
public interface RepoExercicio extends JpaRepository<ExercicioEntity, Long> {
    
}
