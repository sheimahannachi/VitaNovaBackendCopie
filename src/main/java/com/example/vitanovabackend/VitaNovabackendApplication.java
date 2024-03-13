package com.example.vitanovabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity

@SpringBootApplication
public class VitaNovabackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(VitaNovabackendApplication.class, args);
    }

}
