package com.pi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.model.entity.Treino;

public interface RepoTreino extends JpaRepository<Treino, Integer> {
    
}
