package com.pi.logic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.logic.converter.TreinoConverter;
import com.pi.logic.util.JWTUtil;
import com.pi.model.dto.TreinoRequest;
import com.pi.model.dto.TreinoResponse;
import com.pi.model.entity.ClienteEntity;
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
                .subtitulo(request.getSubtitulo())
                .exercicios(new ArrayList<>())
                .build();

        return TreinoConverter.toResponse(treinoRepository.save(novoTreino));
    }

    public void updateTreino(Long id, TreinoRequest request) throws Exception {
        int affectedRows = treinoRepository.atualizarTreino(request.getTitulo(), request.getSubtitulo(), id);
        if (affectedRows < 1) {
            throw new Exception("Não foi possível apagar o treino.");
        }
    }

    public void deleteTreino(Long id) throws Exception {
        int affectedRows = treinoRepository.apagarTreino(id);
        if (affectedRows < 1) {
            throw new Exception("Não foi possível atualizar nenhum treino.");
        }
    }

    public List<TreinoResponse> getTreinos(String token, Long clienteID) throws Exception {
        jwtUtil.formatToken(token);
        List<TreinoEntity> treinos = treinoRepository.treinosDoCliente(clienteID);

        treinos.stream()
                .forEach((treino) -> treino
                        .setExercicios(exercicioRepository.encontrarTodosComTreinoID(treino.getId())));

        return TreinoConverter.toResponseList(treinos);
    }
}
