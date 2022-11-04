package com.pi.logic.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pi.logic.converter.ProfissionalConverter;
import com.pi.logic.util.JWTUtil;
import com.pi.logic.util.Pair;
import com.pi.model.dto.LoginRequest;
import com.pi.model.dto.ProfissionalRequest;
import com.pi.model.dto.ProfissionalResponse;
import com.pi.model.entity.ProfissionalEntity;
import com.pi.model.repository.ClienteRepository;
import com.pi.model.repository.ProfissionalRepository;

@Service
public class ProfissionalService {
    
    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    public boolean emailExiste(String email) {
        return profissionalRepository.encontrarPorEmail(email).isPresent();
    }

    public boolean cpfExiste(String cpf) {
        return profissionalRepository.encontrarPorCPF(cpf).isPresent();
    }

    public boolean cnpjExiste(String cnpj) {
        return profissionalRepository.encontrarPorCNPJ(cnpj).isPresent();
    }

    public ProfissionalResponse registrar(ProfissionalRequest request) throws Exception {

        if (emailExiste(request.getEmail()) || clienteRepository.encontrarPorEmail(request.getEmail()).isPresent()) {
            throw new Exception("E-mail já cadastrado.");
        }

        if (cpfExiste(request.getCpf())) {
            throw new Exception("CPF já cadastrado.");
        }

        if (cnpjExiste(request.getCnpj())) {
            throw new Exception("CNPJ já cadastrado.");
        }

        ProfissionalEntity entity = ProfissionalConverter.toEntity(request);
        entity.setEmail(request.getEmail().toLowerCase());
        entity.setSenha(passwordEncoder.encode(request.getSenha()));
        return ProfissionalConverter.toResponse(profissionalRepository.save(entity));
    }

    public Pair<ProfissionalResponse, String> authenticate(LoginRequest request) throws Exception {

        Optional<ProfissionalEntity> optionalProfissional = profissionalRepository.encontrarPorEmail(request.getEmail());

        if (optionalProfissional.isEmpty()) {
            throw new Exception(String.format("Conta com e-mail %s não contrada.", request.getEmail()));
        }

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtil.generateToken(optionalProfissional.get());
            ProfissionalResponse response = ProfissionalConverter.toResponse(optionalProfissional.get());
            return new Pair<ProfissionalResponse,String>(response, token);
        } catch (Exception e) {
            throw new Exception("Senha incorreta.");
        } finally {
            
        }
    } 

}
