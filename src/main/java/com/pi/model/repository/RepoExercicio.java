package com.pi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.Exercicio;

@Repository
public interface RepoExercicio extends JpaRepository<Exercicio, Long> {
    
}
