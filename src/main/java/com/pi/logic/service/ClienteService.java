package com.pi.logic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    @Autowired
    private JavaMailSender javaMailSender;

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

        String generatedPassword = passwordGenerator.generateCommonLangPassword();
        ClienteEntity entity = ClienteConverter.toEntity(request);
        entity.setProfissional(optionalProfissional.get());
        entity.setEmail(request.getEmail().toLowerCase());
        entity.setSenha(passwordEncoder.encode(generatedPassword));
        ClienteResponse response = ClienteConverter.toResponse(clienteRepository.save(entity));
        enviarEmailCadastroCliente(response.getNome(), response.getEmail(), generatedPassword);
        return response;
    }

    private void enviarEmailCadastroCliente(String nome, String email, String senha) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("manhattandummymail@gmail.com");
        message.setTo(email);
        message.setSubject("Cadastro ");
        message.setText(String.format("Olá, %s\n\nSeu cadastro foi criado com sucesso.\n\nSua senha é %s\n\nFaça login e altera-a.", nome, senha));
        javaMailSender.send(message);
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

    public List<ClienteResponse> todosClientesDoProfissional(String token) throws Exception {
        String formattedToken = jwtUtil.formatToken(token);
        Long profissionalID = jwtUtil.getIdFromToken(formattedToken);
        List<ClienteEntity> entityList = clienteRepository.todosClientesComProfissional(profissionalID);
        return entityList
                .stream()
                .map((cliente) -> ClienteConverter.toResponse(cliente))
                .collect(Collectors.toList());
    }

}
