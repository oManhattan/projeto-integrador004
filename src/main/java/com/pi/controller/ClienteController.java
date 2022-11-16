package com.pi.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.logic.service.ClienteService;
import com.pi.model.dto.ClienteRequest;
import com.pi.model.dto.ClienteResponse;

@CrossOrigin(origins = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ROLE_PROFISSIONAL')")
    public ResponseEntity<?> register(@RequestHeader(name = "Authorization") String token, @RequestBody ClienteRequest request) {
        try {
            ClienteResponse response = clienteService.registrar(token, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_PROFISSIONAL')")
    public ResponseEntity<?> getAllClients(@RequestHeader(name = "Authorization") String token) {
        try {
            List<ClienteResponse> response = clienteService.todosClientesDoProfissional(token);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/alterar-perfil")
    @PreAuthorize("hasAuthority('ROLE_CLIENTE')")
    public ResponseEntity<?> alterarPerfil(@RequestHeader(name = "Authorization") String token, @RequestBody ClienteRequest request) {
        try {
            clienteService.alterarPerfilCliente(token, request);
            return ResponseEntity.ok().body("Informações alteradas");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarClientePorID(@RequestHeader(name = "Authorization") String token, @RequestParam(name = "id") Long id) {
        try {
            ClienteResponse response = clienteService.buscarPerfil(id);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/apagar-perfil")
    @PreAuthorize("hasAuthority('ROLE_CLIENTE')")
    public ResponseEntity<?> apagarPerfil(@RequestHeader(name = "Authorization") String token) {
        try {
            clienteService.apagarPerfil(token);
            return ResponseEntity.ok().body("Perfil deletado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
