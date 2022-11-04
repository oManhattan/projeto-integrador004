package com.pi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.pi.model.dto.ClienteRequest;

@RestController
public class ClienteController {

    public ResponseEntity<?> register(@RequestHeader(name = "Authentication") String token, ClienteRequest request) {
        return null;
    }

}
