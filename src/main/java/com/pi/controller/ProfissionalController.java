package com.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.logic.service.ProfissionalService;
import com.pi.model.dto.ProfissionalRequest;
import com.pi.model.dto.ProfissionalResponse;

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

}
