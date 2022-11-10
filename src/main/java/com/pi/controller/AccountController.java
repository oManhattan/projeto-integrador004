package com.pi.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.logic.converter.ClienteConverter;
import com.pi.logic.converter.ProfissionalConverter;
import com.pi.logic.service.AccountService;
import com.pi.logic.util.Pair;
import com.pi.model.dto.AlterarSenhaRequest;
import com.pi.model.dto.ClienteResponse;
import com.pi.model.dto.EmailRequest;
import com.pi.model.dto.LoginRequest;
import com.pi.model.dto.ProfissionalResponse;
import com.pi.model.entity.ClienteEntity;
import com.pi.model.entity.ProfissionalEntity;
import com.pi.model.entity.UserAccount;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse httpResponse) {
        try {
            Pair<UserAccount, String> response = accountService.authenticateUser(request);
            if (response.getA() instanceof ProfissionalEntity) {
                ProfissionalResponse profissional = ProfissionalConverter
                        .toResponse((ProfissionalEntity) response.getA());
                Cookie authorization = new Cookie("Authorization", response.getB());
                httpResponse.addCookie(authorization);
                return ResponseEntity.ok()
                        .header("CustomerType", "Profissional")
                        .body(profissional);
            }

            if (response.getA() instanceof ClienteEntity) {
                ClienteResponse cliente = ClienteConverter.toResponse((ClienteEntity) response.getA());
                Cookie authorization = new Cookie("Authorization", response.getB());
                httpResponse.addCookie(authorization);
                return ResponseEntity.ok()
                        .header("CustomerType", "Cliente")
                        .body(cliente);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha interna no login");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/esqueceu-senha")
    public ResponseEntity<?> forgotPassword(@RequestBody EmailRequest email) {
        try {
            accountService.forgotPasswordRequest(email.getEmail());
            return ResponseEntity.ok().body("Senha alterada.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("alterar-senha")
    public ResponseEntity<?> changePasswordRequest(@RequestHeader(name = "Authorization") String token,
            @RequestBody AlterarSenhaRequest request) {
        try {
            accountService.changePassword(token, request);
            return ResponseEntity.ok().body("Senha alterada.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
