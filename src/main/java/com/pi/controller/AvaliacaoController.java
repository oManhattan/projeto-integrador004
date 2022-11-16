package com.pi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.logic.service.AvaliacaoService;
import com.pi.model.dto.AvaliacaoRequest;
import com.pi.model.dto.AvaliacaoResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/avaliacao")
public class AvaliacaoController {
    
    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_PROFISSIONAL')")
    public ResponseEntity<?> criarAvaliacao(@RequestHeader(name = "Authorization", required = true) String token, @RequestParam("cliente-id") Long clienteID, @RequestBody AvaliacaoRequest request) {
        try {
            AvaliacaoResponse response = avaliacaoService.criarAvaliacao(token, clienteID, request);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('ROLE_CLIENTE')")
    public ResponseEntity<?> encontrarListaAvaliacaoCliente(@RequestHeader(name = "Authorization") String token) {
        try {
            List<AvaliacaoResponse> response = avaliacaoService.encontrarListaAvaliacoes(token);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
