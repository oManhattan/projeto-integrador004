package com.pi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.AvaliacaoCorporal;

@Repository
public interface RepoAvaliacaoCorporal extends JpaRepository<AvaliacaoCorporal, Integer> {
    
}
