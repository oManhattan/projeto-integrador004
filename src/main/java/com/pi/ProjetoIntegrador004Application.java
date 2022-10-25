package com.pi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class ProjetoIntegrador004Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoIntegrador004Application.class, args);
	}

	@GetMapping("/")
	public ResponseEntity<String> testeServer() {
		return new ResponseEntity<String>("Ta funcionando", HttpStatus.ACCEPTED);
	}
}
