package com.pi.logic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.logic.converter.ExercicioConverter;
import com.pi.logic.util.JWTUtil;
import com.pi.model.dto.ExercicioRequest;
import com.pi.model.dto.ExercicioResponse;
import com.pi.model.entity.ClienteEntity;
import com.pi.model.entity.ExercicioEntity;
import com.pi.model.entity.TreinoEntity;
import com.pi.model.repository.ClienteRepository;
import com.pi.model.repository.ExercicioRepository;
import com.pi.model.repository.TreinoRepository;

@Service
public class ExercicioService {
    
    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JWTUtil jwtUtil;

    private Boolean clienteCadastradoOuVinculado(String token, Long clienteID) throws Exception {

        String formattedToken = jwtUtil.formatToken(token);
        Long profissionalID = jwtUtil.getIdFromToken(formattedToken);

        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorIdComProfissional(clienteID, profissionalID);
        
        if (optionalCliente.isEmpty()) {
            return false;
        }

        return true;
    }

    public ExercicioResponse criarExercicio(String token, Long clienteID, Long treinoID, ExercicioRequest request) throws Exception {
        
        if (!clienteCadastradoOuVinculado(token, clienteID)) {
            throw new Exception("Cliente não cadastrado ou não vinculado ao profissional.");
        }

        Optional<TreinoEntity> optionalTreino = treinoRepository.encontrarTreinoPorIdComCliente(treinoID, clienteID);

        if (optionalTreino.isEmpty()) {
            throw new Exception("Treino não cadastrado ou não vinculado ao cliente.");
        }

        ExercicioEntity entity = ExercicioConverter.toEntity(request);
        entity.setTreino(optionalTreino.get());

        return ExercicioConverter.toResponse(exercicioRepository.save(entity));
    }

    public void excluirExercicio(Long exercicioID) {
        exercicioRepository.deleteById(exercicioID);
    }
}
