package com.pi.logic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.logic.util.JWTUtil;
import com.pi.model.dto.TreinoRequest;
import com.pi.model.entity.ClienteEntity;
import com.pi.model.entity.TreinoEntity;
import com.pi.model.repository.ClienteRepository;
import com.pi.model.repository.TreinoRepository;

@Service
public class TreinoService {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TreinoRepository treinoRepository;

    public TreinoEntity createTreino(String token, Long clienteID, TreinoRequest request) throws Exception {
        jwtUtil.formatToken(token);

        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorId(clienteID);

        if (optionalCliente.isEmpty()) {
            throw new Exception("Cliente não encontrado.");
        }

        TreinoEntity novoTreino = TreinoEntity
                .builder()
                .cliente(optionalCliente.get())
                .titulo(request.getTitulo())
                .subtitulo(request.getSubtitulo())
                .build();

        return treinoRepository.save(novoTreino);
    }

    public void deleteTreino(String token, Long id, TreinoRequest request) throws Exception {
        jwtUtil.formatToken(token);
        int affectedRows = treinoRepository.atualizarTreino(request.getTitulo(), request.getSubtitulo(), id);
        if (affectedRows < 1) {
            throw new Exception("Não foi possível apagar o treino.");
        }
    }

    public void updateTreino(String token, Long id) throws Exception {
        jwtUtil.formatToken(token);
        int affectedRows = treinoRepository.apagarTreino(id);
        if (affectedRows < 1) {
            throw new Exception("Não foi possível atualizar nenhum treino.");
        }
    }

    public List<TreinoEntity> getTreinos(String token, Long clienteID) throws Exception {
        jwtUtil.formatToken(token);
        List<TreinoEntity> treinos = treinoRepository.treinosDoCliente(clienteID);
        return treinos;
    }
}
