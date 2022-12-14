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
import com.pi.model.dto.AlterarSenhaRequest;
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

        Optional<ProfissionalEntity> profissional = profissionalRepository.encontrarPorEmail(request.getEmail());

        if (profissional.isPresent()) {
            authenticateCredentials(request.getEmail(), request.getPassword(), new ArrayList<>());
            String token = jwtUtil.generateToken(profissional.get().getId(), profissional.get().getEmail(),
                    profissional.get().getAuthority().getAuthority());
            return new Pair<UserAccount, String>(profissional.get(), token);
        }

        Optional<ClienteEntity> cliente = clienteRepository.encontrarPorEmail(request.getEmail());

        if (cliente.isPresent()) {
            authenticateCredentials(request.getEmail(), request.getPassword(), new ArrayList<>());
            String token = jwtUtil.generateToken(cliente.get().getId(), cliente.get().getEmail(),
                    cliente.get().getAuthority().getAuthority());
            return new Pair<UserAccount, String>(cliente.get(), token);
        }

        throw new UsernameNotFoundException(String.format("Conta n??o encontrada com o e-mail %s", request.getEmail()));
    }

    public void forgotPasswordRequest(String email) throws Exception {

        String novaSenha = passwordGenerator.generateCommonLangPassword();
        System.out.println(String.format("Nova senha -> %s", novaSenha));
        String senhaCriptografa = passwordEncoder.encode(novaSenha);

        int profissionalRows = profissionalRepository.alterarSenha(senhaCriptografa, email);
        System.out.println(String.format("%d linhas de profissional afetadas", profissionalRows));
        if (profissionalRows > 0) {
            sendMail(email, "Alterar Senha - SmartTraining", String.format("Ol??\n\nSua nova senha ?? %s\n\nAcesse sua conta para alter??-la.", novaSenha));
            return;
        }
        
        int clienteRows = clienteRepository.alterarSenha(senhaCriptografa, email);
        System.out.println(String.format("%d linhas de cliente afetadas", clienteRows));
        if (clienteRows > 0) {
            sendMail(email, "Alterar Senha - SmartTraining", String.format("Ol??\n\nSua nova senha ?? %s\n\nAcesse sua conta para alter??-la.", novaSenha));
        }
        
        throw new Exception("E-mail n??o encontrado.");
    }

    public void changePassword(String token, AlterarSenhaRequest request) throws Exception {

        token = jwtUtil.formatToken(token);
        String email = jwtUtil.getUsernameFromToken(token);
     
        authenticateCredentials(email, request.getSenhaAtual(), new ArrayList<>());

        String encodedPassword = passwordEncoder.encode(request.getNovaSenha());
        String role = jwtUtil.getUserAuthorityFromToken(token);
        
        if (role.equals("ROLE_PROFISSIONAL")) {
            profissionalRepository.alterarSenha(encodedPassword, email);
        } else if (role.equals("ROLE_CLIENTE")) {
            clienteRepository.alterarSenha(encodedPassword, email);
        } else {
            throw new Exception("Erro interno");
        }

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
            System.out.println(e);
            throw new Exception("Senha incorreta.");
        }
    }

}
