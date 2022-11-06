package com.pi.logic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pi.logic.util.JWTUtil;
import com.pi.logic.util.Pair;
import com.pi.logic.util.PasswordGeneratorUtil;
import com.pi.model.dto.LoginRequest;
import com.pi.model.entity.ClienteEntity;
import com.pi.model.entity.ProfissionalEntity;
import com.pi.model.entity.UserAccount;
import com.pi.model.repository.ClienteRepository;
import com.pi.model.repository.ProfissionalRepository;

@Service
public class AccountService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PasswordGeneratorUtil passwordGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Pair<UserAccount, String> authenticateUser(LoginRequest request) throws Exception {

        authenticateCredentials(request.getEmail(), request.getPassword(), new ArrayList<>());

        Optional<ProfissionalEntity> profissional = profissionalRepository.encontrarPorEmail(request.getEmail());

        if (profissional.isPresent()) {
            String token = jwtUtil.generateToken(profissional.get().getId(), profissional.get().getEmail(),
                    profissional.get().getAuthority().getAuthority());
            return new Pair<UserAccount, String>(profissional.get(), token);
        }

        Optional<ClienteEntity> cliente = clienteRepository.encontrarPorEmail(request.getEmail());

        if (cliente.isPresent()) {
            String token = jwtUtil.generateToken(cliente.get().getId(), cliente.get().getEmail(),
                    cliente.get().getAuthority().getAuthority());
            return new Pair<UserAccount, String>(cliente.get(), token);
        }

        throw new UsernameNotFoundException(String.format("Conta não encontrada com o e-mail %s", request.getEmail()));
    }

    public void newPasswordRequest(String email) throws Exception {

        String novaSenha = passwordGenerator.generateCommonLangPassword();
        String senhaCriptografa = passwordEncoder.encode(novaSenha);

        int profissionalRows = profissionalRepository.alterarSenha(senhaCriptografa, email);

        if (profissionalRows > 0) {
            return;
        }
        
        int clienteRows = clienteRepository.alterarSenha(senhaCriptografa, email);

        if (clienteRows <= 0) {
            throw new Exception("E-mail não encontrado.");
        }
        
        sendMail(email, "Alterar Senha - SmartTraining", String.format("Olá\n\nSua nova senha é %s\n\nAcesse sua conta para alterá-la.", novaSenha));
    }

    public void changePassword(String token, String password) throws Exception {

    }

    private void sendMail(String email, String assunto, String mensagem) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("manhattandummymail@gmail.com");
        message.setTo(email);
        message.setSubject(assunto);
        message.setText(mensagem);
        javaMailSender.send(message);
    }

    private void authenticateCredentials(String email, String password, List<GrantedAuthority> authorities)
            throws Exception {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password, authorities));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new Exception("Senha incorreta.");
        }
    }

}
