package com.pi.logic.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pi.model.entity.ClienteEntity;
import com.pi.model.entity.ProfissionalEntity;
import com.pi.model.repository.ClienteRepository;
import com.pi.model.repository.ProfissionalRepository;

@Service
public class UserLoginService implements UserDetailsService {
    
    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ProfissionalEntity> optionalProfissional = profissionalRepository.encontrarPorEmail(username);

        if (optionalProfissional.isPresent()) {
            return new User(optionalProfissional.get().getEmail().toLowerCase(), optionalProfissional.get().getSenha(), new ArrayList<>());
        }

        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorEmail(username);

        if (optionalCliente.isPresent()) {
            return new User(optionalCliente.get().getEmail().toLowerCase(), optionalCliente.get().getSenha(), new ArrayList<>());
        }

        throw new UsernameNotFoundException(String.format("Conta não encontrada com o e-mail %s", username));
    }
}
