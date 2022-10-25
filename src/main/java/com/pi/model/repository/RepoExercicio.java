package com.pi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.model.entity.Exercicio;

public interface RepoExercicio extends JpaRepository<Exercicio, Integer> {
    
}
