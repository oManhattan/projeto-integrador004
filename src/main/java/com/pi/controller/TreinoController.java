package com.pi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.logic.service.TreinoService;
import com.pi.model.dto.TreinoRequest;
import com.pi.model.dto.TreinoResponse;

@RestController
@RequestMapping("/api/v1/exercicio")
public class TreinoController {
    
    @Autowired
    private TreinoService treinoService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_PROFISSIONAL')")
    public ResponseEntity<?> criarTreino(@RequestHeader("Authorization") String token, @RequestParam("/cliente-id") Long id, @RequestBody TreinoRequest request) {
        try {
            TreinoResponse response = treinoService.createTreino(token, id, request);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_PROFISSIONAL')")
    public ResponseEntity<?> updateTreino(@RequestHeader("Authorization") String token, @RequestParam("treino-id") Long id, @RequestBody TreinoRequest request) {
        try {
            treinoService.updateTreino(id, request);
            return ResponseEntity.ok().body("Treino atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_PROFISSIONAL')")
    public ResponseEntity<?> deleteTreino(@RequestHeader("Authorization") String token, @RequestParam("treino-id") Long id) {
        try {
            treinoService.deleteTreino(id);
            return ResponseEntity.ok().body("Treino apagado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> getTreinoList(@RequestHeader("Authorization") String token, @RequestParam("cliente-id") Long id) {
        try {
            List<TreinoResponse> response = treinoService.getTreinos(token, id);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
