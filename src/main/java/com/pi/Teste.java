package com.pi;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Teste {
    @RequestMapping("/xaolin")
    public String teste() {
        return "Xaolin Matador de Porco";
    }   
}
