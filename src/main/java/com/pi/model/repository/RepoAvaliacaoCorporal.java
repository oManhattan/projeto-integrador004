package com.pi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.model.entity.AvaliacaoCorporal;

public interface RepoAvaliacaoCorporal extends JpaRepository<AvaliacaoCorporal, Integer> {
    
}
