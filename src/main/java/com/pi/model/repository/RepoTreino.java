package com.pi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.Treino;

@Repository
public interface RepoTreino extends JpaRepository<Treino, Integer> {
    
}
