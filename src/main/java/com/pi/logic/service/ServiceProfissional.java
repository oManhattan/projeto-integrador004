package com.pi.logic.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.pi.model.repository.RepoProfissional;

public class ServiceProfissional {
    
    @Autowired
    RepoProfissional repoProfissional;

    public boolean emailExiste(String email) {
        return repoProfissional.emailExiste(email).isPresent();
    }

    public boolean cpfExiste(String cpf) {
        return repoProfissional.cpfExiste(cpf).isPresent();
    }

    public boolean cnpjExiste(String cnpj) {
        return repoProfissional.cnpjExiste(cnpj).isPresent();
    }

    
}
