package com.pi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.TreinoEntity;

@Repository
public interface RepoTreino extends JpaRepository<TreinoEntity, Long> {
    
}
