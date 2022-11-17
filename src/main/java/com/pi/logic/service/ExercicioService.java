package com.pi.logic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.model.dto.ExercicioRequest;
import com.pi.model.dto.ExercicioResponse;
import com.pi.model.repository.ExercicioRepository;
import com.pi.model.repository.TreinoRepository;

@Service
public class ExercicioService {
    
    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private TreinoRepository treinoRepository;

    public ExercicioResponse criarExercicio(Long clienteID, Long treinoID, ExercicioRequest request) {
        
    }
}
