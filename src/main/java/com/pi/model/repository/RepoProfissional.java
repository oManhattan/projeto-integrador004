package com.pi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.model.entity.Profissional;

public interface RepoProfissional extends JpaRepository<Profissional, Integer> {
    
}
