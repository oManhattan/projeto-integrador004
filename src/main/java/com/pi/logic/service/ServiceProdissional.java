package com.pi.logic.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.pi.model.repository.RepoProfissional;

public class ServiceProdissional {
    
    @Autowired
    RepoProfissional repoProfissional;

    public boolean emailExiste() {

        return false; 
    }

    public boolean cpfExiste() {
        
        return false;
    }

    public boolean cnpjExiste() {

        return false;
    }
}
