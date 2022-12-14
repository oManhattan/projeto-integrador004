package com.pi.logic.service;

import java.time.LocalDate;
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

import com.pi.logic.converter.AvaliacaoConverter;
import com.pi.logic.converter.ClienteConverter;
import com.pi.logic.converter.TreinoConverter;
import com.pi.logic.util.JWTUtil;
import com.pi.logic.util.Pair;
import com.pi.logic.util.PasswordGeneratorUtil;
import com.pi.model.dto.AvaliacaoResponse;
import com.pi.model.dto.ClienteRequest;
import com.pi.model.dto.ClienteResponse;
import com.pi.model.dto.LoginRequest;
import com.pi.model.dto.PaginaClienteResponse;
import com.pi.model.entity.ClienteEntity;
import com.pi.model.entity.ProfissionalEntity;
import com.pi.model.entity.TreinoEntity;
import com.pi.model.repository.AvaliacaoRepository;
import com.pi.model.repository.ClienteRepository;
import com.pi.model.repository.ExercicioRepository;
import com.pi.model.repository.ProfissionalRepository;
import com.pi.model.repository.TreinoRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

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
    
    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private ValidacaoService validacaoService;

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
            throw new Exception("Token inv??lido. Fa??a login novamente.");
        }

        if (!validacaoService.CPFisValid(request.getCpf())) {
            throw new Exception("CPF inv??lido.");
        }

        if (emailExiste(request.getEmail())
                || profissionalRepository.encontrarPorEmail(request.getEmail()).isPresent()) {
            throw new Exception("E-mail j?? cadastrado.");
        }

        if (cpfExiste(request.getCpf())) {
            throw new Exception("CPF j?? cadastrado.");
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
        message.setText(String.format(
                "Ol??, %s\n\nSeu cadastro foi criado com sucesso.\n\nSua senha ?? %s\n\nFa??a login e altera-a.", nome,
                senha));
        javaMailSender.send(message);
    }

    public ClienteResponse authenticate(LoginRequest request) throws Exception {

        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorEmail(request.getEmail());

        if (optionalCliente.isEmpty()) {
            throw new Exception(String.format("Conta com e-mail %s n??o encontrada.", request.getEmail()));
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

    public void alterarPerfilCliente(String token, ClienteRequest request) throws Exception {
        String formattedToken = jwtUtil.formatToken(token);
        Long id = jwtUtil.getIdFromToken(formattedToken);
        String tokenEmail = jwtUtil.getUsernameFromToken(formattedToken);

        if (clienteRepository.emailExiste(request.getEmail()) && !request.getEmail().equals(tokenEmail)) {
            throw new Exception("E-mail j?? cadastrado");
        }

        clienteRepository.alterarPerfil(request.getEmail(), request.getNome(), request.getSobrenome(),
                request.getGenero().name(), request.getDataNascimento(), id);
    }

    public ClienteResponse buscarPerfil(Long id) throws Exception {
        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorId(id);

        if (optionalCliente.isEmpty()) {
            throw new Exception("Perfil n??o encontrado.");
        }

        return ClienteConverter.toResponse(optionalCliente.get());
    }

    public void apagarPerfil(String token) throws Exception {
        String formattedToken = jwtUtil.formatToken(token);
        Long id = jwtUtil.getIdFromToken(formattedToken);
        int affectedRows = clienteRepository.deletarPerfil(id);

        if (affectedRows <= 0) {
            throw new Exception("N??o foi poss??vel apagar o perfil.");
        }
    }

    public PaginaClienteResponse carregarPaginaCliente(String token, Long clienteID) throws Exception {

        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorId(clienteID);

        if (optionalCliente.isEmpty()) {
            throw new Exception("Cliente n??o encontrado.");
        }

        List<AvaliacaoResponse> listaAvaliacao = AvaliacaoConverter.toResponseList(avaliacaoRepository.encontrarTodasAvaliacoesDoCliente(clienteID));

        List<Pair<Float, LocalDate>> graficoPeso = listaAvaliacao
        .stream()
        .map((avaliacao) -> new Pair<Float, LocalDate>(avaliacao.getPeso(), avaliacao.getDataRealizacao()))
        .collect(Collectors.toList());

        AvaliacaoResponse ultimaAvaliacao = listaAvaliacao.stream().findFirst().orElse(null);

        List<TreinoEntity> treinos = treinoRepository.treinosDoCliente(clienteID);

        treinos.stream()
                .forEach((treino) -> treino
                        .setExercicios(exercicioRepository.encontrarTodosComTreinoID(treino.getId())));

        return PaginaClienteResponse.builder()
        .cliente(ClienteConverter.toResponse(optionalCliente.get()))
        .ultimaAvaliacao(ultimaAvaliacao)
        .graficoPeso(graficoPeso)
        .treinos(TreinoConverter.toResponseList(treinos))
        .build();
    }
}
