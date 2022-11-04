package com.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.logic.service.ProfissionalService;
import com.pi.logic.util.Pair;
import com.pi.model.dto.LoginRequest;
import com.pi.model.dto.ProfissionalRequest;
import com.pi.model.dto.ProfissionalResponse;

@RestController
@RequestMapping("/profissional")
public class ProfissionalController {
    
    @Autowired
    private ProfissionalService profissionalService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Pair<ProfissionalResponse, String> pair = profissionalService.authenticate(request);
            ProfissionalResponse response = pair.getA();
            String token = pair.getB();
            return ResponseEntity.ok().header("Authorization", token).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ProfissionalRequest reqeust) {
        try {
            ProfissionalResponse response = profissionalService.registrar(reqeust);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // public ResponseEntity<?> todosClientes(@RequestHeader(name = "Authorization") String token) {

    // }

}
