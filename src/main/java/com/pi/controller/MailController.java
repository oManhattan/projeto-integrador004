package com.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.logic.service.MailService;

@RestController
@RequestMapping("/mail")
public class MailController {
    
    @Autowired
    private MailService mailService;

    @GetMapping("/test")
    public ResponseEntity<?> sendTestMail(@RequestParam(name = "mailTo") String email) {
        mailService.sendSimpleMessage(email, "E-mail teste", "Isso é apenas uma mensagem teste.");
        return ResponseEntity.ok().body(null);
    }

}
