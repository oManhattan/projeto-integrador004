package com.pi.controller;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pi.logic.util.JWTUtil;

@CrossOrigin(origins = "*", exposedHeaders = "*")
@Controller
public class PaginasController {

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/")
    public ModelAndView usuarioLogado(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return new ModelAndView("login");
        }

        try {
            String authorization = "";
            Cookie authorizationCookie = Arrays.asList(request.getCookies()).stream()
                    .filter((cookie) -> cookie.getName().equals("Authorization")).findFirst()
                    .orElseThrow();
            authorization = authorizationCookie.getValue().replace("$", " ");
            String formattedToken = jwtUtil.formatToken(authorization);
            String customerType = jwtUtil.getUserAuthorityFromToken(formattedToken);
            if (customerType.equals("ROLE_PROFISSIONAL")) {
                return new ModelAndView("profissional");
            } else if (customerType.equals("ROLE_CLIENTE")) {
                return new ModelAndView("cliente");
            } else {
                return new ModelAndView("login");
            }
        } catch (Exception e) {
            return new ModelAndView("login");
        }
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
