package com.pi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.model.entity.ClienteEntity;

@Repository
public interface RepoCliente extends JpaRepository<ClienteEntity, Long> {
    
}
