package com.pi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.model.entity.Cliente;

public interface RepoCliente extends JpaRepository<Cliente, Integer> {
    
}
