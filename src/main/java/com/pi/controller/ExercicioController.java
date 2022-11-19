package com.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.logic.service.ExercicioService;
import com.pi.model.dto.ExercicioRequest;
import com.pi.model.dto.ExercicioResponse;

@CrossOrigin(origins = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/api/v1/exercicio")
public class ExercicioController {
    
    @Autowired
    private ExercicioService exercicioService;

    @PostMapping("/create")
    public ResponseEntity<?> createExercicio(@RequestHeader("Authorization") String token, @RequestParam("clienteid") Long clienteID, @RequestParam("treinoid") Long treinoID, @RequestBody ExercicioRequest request) {
        try {
            ExercicioResponse response = exercicioService.criarExercicio(token, clienteID, treinoID, request);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
