package com.pi.logic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.logic.converter.ExercicioConverter;
import com.pi.logic.converter.TreinoConverter;
import com.pi.logic.util.JWTUtil;
import com.pi.model.dto.TreinoRequest;
import com.pi.model.dto.TreinoResponse;
import com.pi.model.entity.ClienteEntity;
import com.pi.model.entity.ExercicioEntity;
import com.pi.model.entity.TreinoEntity;
import com.pi.model.repository.ClienteRepository;
import com.pi.model.repository.ExercicioRepository;
import com.pi.model.repository.TreinoRepository;

@Service
public class TreinoService {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private ExercicioRepository exercicioRepository;

    public TreinoResponse createTreino(String token, Long clienteID, TreinoRequest request) throws Exception {
        String formattedToken = jwtUtil.formatToken(token);
        Long profissionalID = jwtUtil.getIdFromToken(formattedToken);

        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorIdComProfissional(clienteID,
                profissionalID);

        if (optionalCliente.isEmpty()) {
            throw new Exception("Cliente não encontrado ou não vinculado ao profissional.");
        }

        if (treinoRepository.treinoJaCadastrado(request.getTitulo(), clienteID)) {
            throw new Exception("Treino já cadastrado");
        }

        TreinoEntity novoTreino = TreinoEntity
                .builder()
                .cliente(optionalCliente.get())
                .titulo(request.getTitulo())
                .exercicios(new ArrayList<>())
                .build();

        return TreinoConverter.toResponse(treinoRepository.save(novoTreino));
    }

    public void updateTreino(Long id, TreinoRequest request) throws Exception {
        int affectedRows = treinoRepository.atualizarTreino(request.getTitulo(), id);
        if (affectedRows < 1) {
            throw new Exception("Não foi possível apagar o treino.");
        }
    }

    public void deleteTreino(Long id) throws Exception {
        Optional<TreinoEntity> entity = treinoRepository.findById(id);

        if (entity.isEmpty()) {
            throw new Exception("Treino não encontrado.");
        }

        treinoRepository.delete(entity.get());
    }

    public List<TreinoResponse> getTreinos(String token, Long clienteID) throws Exception {
        jwtUtil.formatToken(token);

        List<TreinoEntity> treinos = treinoRepository.treinosDoCliente(clienteID);

        treinos.stream()
                .forEach((treino) -> treino
                        .setExercicios(exercicioRepository.encontrarTodosComTreinoID(treino.getId())));

        return TreinoConverter.toResponseList(treinos);
    }

    public void apagarTodosTreinosCliente(String token, Long clienteID) throws Exception {

        String formattedToken = jwtUtil.formatToken(token);
        Long profissionalID = jwtUtil.getIdFromToken(formattedToken);

        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorIdComProfissional(clienteID,
                profissionalID);

        if (optionalCliente.isEmpty()) {
            throw new Exception("Cliente não encontrado ou não vinculado ao profissional.");
        }

        List<TreinoEntity> treinos = treinoRepository.treinosDoCliente(clienteID);

        treinos.forEach((treino) -> {
            treinoRepository.delete(treino);
        });

    }

    public List<TreinoResponse> salvarListaTreinos(String token, Long clienteID, List<TreinoRequest> treinos)
            throws Exception {

        String formattedToken = jwtUtil.formatToken(token);
        Long profissionalID = jwtUtil.getIdFromToken(formattedToken);

        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorIdComProfissional(clienteID,
                profissionalID);

        if (optionalCliente.isEmpty()) {
            throw new Exception("Cliente não encontrado ou não vinculado ao profissional.");
        }

        for (TreinoRequest r : treinos) {
            if (treinoRepository.treinoJaCadastrado(r.getTitulo(), clienteID)) {
                throw new Exception(String.format("Treino %S já cadastrado.", r.getTitulo()));
            }
        }

        List<TreinoEntity> entities = treinos.stream().map((treinoRequest) -> {
            TreinoEntity treinoEntity = TreinoEntity
                    .builder()
                    .cliente(optionalCliente.get())
                    .titulo(treinoRequest.getTitulo())
                    .build();

            if (treinoRequest.getExercicios() != null) {
                List<ExercicioEntity> exercicios = treinoRequest.getExercicios()
                        .stream()
                        .map((exercicioRequest) -> {
                            ExercicioEntity exercicioEntity = ExercicioConverter.toEntity(exercicioRequest);
                            exercicioEntity.setTreino(treinoEntity);
                            return exercicioEntity;
                        })
                        .collect(Collectors.toList());
                treinoEntity.setExercicios(exercicios);
            } else {
                treinoEntity.setExercicios(new ArrayList<>());
            }

            return treinoEntity;
        }).collect(Collectors.toList());

        List<TreinoResponse> responses = treinoRepository.saveAllAndFlush(entities)
                .stream()
                .map((entity) -> TreinoConverter.toResponse(entity))
                .collect(Collectors.toList());

        return responses;
    }
}
