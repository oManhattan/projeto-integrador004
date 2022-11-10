package com.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.logic.service.ProfissionalService;
import com.pi.model.dto.ProfissionalRequest;
import com.pi.model.dto.ProfissionalResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/profissional")
public class ProfissionalController {
    
    @Autowired
    private ProfissionalService profissionalService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ProfissionalRequest reqeust) {
        try {
            ProfissionalResponse response = profissionalService.registrar(reqeust);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/carregar-perfil")
    @PreAuthorize("hasAuthority('ROLE_PROFISSIONAL')")
    public ResponseEntity<?> carregarPerfil(@RequestHeader("Authorization") String token) {
        try {
            ProfissionalResponse response = profissionalService.carregarPerfil(token);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/alterar-perfil")
    @PreAuthorize("hasAuthority('ROLE_PROFISSIONAL')")
    public ResponseEntity<?> alterarPerfil(@RequestHeader("Authorization") String token, @RequestBody ProfissionalRequest request) {
        try {
            profissionalService.alterarPerfil(token, request);
            return ResponseEntity.ok().body("Alteração feita com sucesso.");
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/apagar-perfil")
    @PreAuthorize("hasAuthority('ROLE_PROFISSIONAL')")
    public ResponseEntity<?> apagarPerfil(@RequestHeader("Authorization") String token) {
        try {
            profissionalService.apagarPerfil(token);
            return ResponseEntity.ok().body("Perfil apagado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
