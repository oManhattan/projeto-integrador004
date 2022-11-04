package com.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.logic.service.ClienteService;
import com.pi.model.dto.ClienteRequest;
import com.pi.model.dto.ClienteResponse;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestHeader(name = "Authorization") String token, @RequestBody ClienteRequest request) {
        try {
            ClienteResponse response = clienteService.registrar(token, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // @GetMapping("/all")
    // public ResponseEntity<?> getAllClients(@RequestHeader(name = "Authorization") String token) {

    // }
}
