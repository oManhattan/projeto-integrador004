package com.pi.logic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pi.logic.converter.ProfissionalConverter;
import com.pi.logic.util.JWTUtil;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ValidacaoService validacaoService;

    public boolean emailExiste(String email) {
        return profissionalRepository.encontrarPorEmail(email).isPresent();
    }

    public boolean cpfExiste(String cpf) {
        return profissionalRepository.encontrarPorCPF(cpf).isPresent();
    }

    public boolean cnpjExiste(String cnpj) {
        return profissionalRepository.encontrarPorCNPJ(cnpj).isPresent();
    }

    public ProfissionalResponse carregarPerfil(String token) throws Exception {
        String formattedToken = jwtUtil.formatToken(token);
        String tokenEmail = jwtUtil.getUsernameFromToken(formattedToken);

        Optional<ProfissionalEntity> optionalProfissional = profissionalRepository.encontrarPorEmail(tokenEmail);
        if (optionalProfissional.isEmpty()) {
            throw new Exception(String.format("Não foi possível encontrar um perfil com e-mail %s", tokenEmail));
        }

        return ProfissionalConverter.toResponse(optionalProfissional.get());
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

        if (!validacaoService.CPFisValid(request.getCpf())) {
            throw new Exception("CPF inválido.");
        }

        if (request.getCnpj() != null && !validacaoService.CNPJisValid(request.getCnpj())) {
            throw new Exception("CNPJ inválido.");
        }

        ProfissionalEntity entity = ProfissionalConverter.toEntity(request);
        entity.setEmail(request.getEmail().toLowerCase());
        entity.setSenha(passwordEncoder.encode(request.getSenha()));
        return ProfissionalConverter.toResponse(profissionalRepository.save(entity));
    }

    public void alterarPerfil(String token, ProfissionalRequest request) throws Exception {
        String formattedToken = jwtUtil.formatToken(token);
        String emailToken = jwtUtil.getUsernameFromToken(formattedToken);

        if (!validacaoService.CPFisValid(request.getCpf())) {
            throw new Exception("CPF inválido.");
        }

        if (request.getCnpj() != null && !validacaoService.CNPJisValid(request.getCnpj())) {
            throw new Exception("CNPJ inválido.");
        }

        if (profissionalRepository.emailExiste(request.getEmail()) && !request.getEmail().equals(emailToken)) {
            throw new Exception("E-mail já cadastrado.");
        }

        Long profissionalID = jwtUtil.getIdFromToken(formattedToken);
        profissionalRepository.alterarPerfil(request.getEmail(), request.getNome(), request.getSobrenome(), request.getCpf(), request.getCnpj(), profissionalID);
    }

    public void apagarPerfil(String token) throws Exception {
        String formattedToken = jwtUtil.formatToken(token);
        Long tokenID = jwtUtil.getIdFromToken(formattedToken);
        int affectedRows = profissionalRepository.apagarPerfil(tokenID);

        if (affectedRows <= 0) {
            throw new Exception("Não foi possível apagar o perfil.");
        }
    }
}
