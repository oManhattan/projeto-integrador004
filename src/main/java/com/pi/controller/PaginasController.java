package com.pi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(origins = "*", exposedHeaders = "*")
@Controller
public class PaginasController {

    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
    
    @GetMapping("/tela-profissional")
    public ModelAndView tela_profissional() {
        return new ModelAndView("profissional");
    }
    
    @GetMapping("/tela-cliente")
    public ModelAndView tela_cliente() {
        return new ModelAndView("cliente");
    }
    
}
