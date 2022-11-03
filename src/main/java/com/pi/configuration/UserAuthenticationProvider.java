package com.pi.configuration;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.pi.model.entity.ClienteEntity;
import com.pi.model.entity.ProfissionalEntity;
import com.pi.model.repository.ClienteRepository;
import com.pi.model.repository.ProfissionalRepository;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String senha = authentication.getCredentials().toString();

        Optional<ProfissionalEntity> optionalProfissional = profissionalRepository.encontrarPorEmail(email);
        
        if (optionalProfissional.isPresent() && passwordEncoder.matches(senha, optionalProfissional.get().getSenha())) {
            return new UsernamePasswordAuthenticationToken(email, senha, new ArrayList<>());
        }

        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorEmail(email);

        if (optionalCliente.isPresent() && passwordEncoder.matches(senha, optionalCliente.get().getSenha())) {
            return new UsernamePasswordAuthenticationToken(email, senha, new ArrayList<>());
        }

        throw new BadCredentialsException("Senha incorreta");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
