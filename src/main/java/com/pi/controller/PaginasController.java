package com.pi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(origins = "*", exposedHeaders = "*")
@Controller
public class PaginasController {
    
    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView home = new ModelAndView("index");

        return home;
    }

    // @GetMapping("/perfil-cliente")
    // @PreAuthorize("hasAuthority('ROLE_CLIENTE')")
    // public ModelAndView telaCliente() {
    //     return new ModelAndView("perfil-cliente");
    // }
}
