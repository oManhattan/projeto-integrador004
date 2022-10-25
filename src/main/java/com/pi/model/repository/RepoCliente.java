package com.pi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.Cliente;

@Repository
public interface RepoCliente extends JpaRepository<Cliente, Integer> {
    
}
