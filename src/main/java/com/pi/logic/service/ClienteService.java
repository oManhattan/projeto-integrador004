package com.pi.logic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pi.logic.converter.ClienteConverter;
import com.pi.logic.util.JWTUtil;
import com.pi.logic.util.PasswordGeneratorUtil;
import com.pi.model.dto.ClienteRequest;
import com.pi.model.dto.ClienteResponse;
import com.pi.model.dto.LoginRequest;
import com.pi.model.entity.ClienteEntity;
import com.pi.model.entity.ProfissionalEntity;
import com.pi.model.repository.ClienteRepository;
import com.pi.model.repository.ProfissionalRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProfissionalRepository profissionalRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private PasswordGeneratorUtil passwordGenerator;

    public Boolean emailExiste(String email) {
        return clienteRepository.encontrarPorEmail(email).isPresent();
    }

    public Boolean cpfExiste(String cpf) {
        return clienteRepository.encontrarPorCPF(cpf).isPresent();
    }

    public ClienteResponse registrar(String token, ClienteRequest request) throws Exception {
        token = jwtUtil.formatToken(token);
        String profissionalUsername = jwtUtil.getUsernameFromToken(token);
        Optional<ProfissionalEntity> optionalProfissional = profissionalRepository
                .encontrarPorEmail(profissionalUsername);

        if (optionalProfissional.isEmpty()) {
            throw new Exception("Token inválido. Faça login novamente.");
        }

        if (emailExiste(request.getEmail())
                || profissionalRepository.encontrarPorEmail(request.getEmail()).isPresent()) {
            throw new Exception("E-mail já cadastrado.");
        }

        if (cpfExiste(request.getCpf())) {
            throw new Exception("CPF já cadastrado.");
        }

        System.out.println(request.toString());
        String generatedPassword = passwordGenerator.generateCommonLangPassword();
        ClienteEntity entity = ClienteConverter.toEntity(request);
        System.out.println("Depois de criar entidade");
        entity.setProfissional(optionalProfissional.get());
        System.out.println("Profissional adicionado");
        entity.setEmail(request.getEmail().toLowerCase());
        System.out.println("Antes da criacao da senha");
        entity.setSenha(passwordEncoder.encode(generatedPassword));
        System.out.println("tentando salvar o cliente");
        return ClienteConverter.toResponse(clienteRepository.save(entity));
    }

    public ClienteResponse authenticate(LoginRequest request) throws Exception {

        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorEmail(request.getEmail());

        if (optionalCliente.isEmpty()) {
            throw new Exception(String.format("Conta com e-mail %s não encontrada.", request.getEmail()));
        }

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ClienteConverter.toResponse(optionalCliente.get());
        } catch (Exception e) {
            throw new Exception("Senha incorreta.");
        }
    }

    

}
