package com.example.vitanovabackend;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    /*public void addCorsMappings(CorsRegistry registry) {
     /*   registry.addMapping("/*").
                allowedOrigins("*").
                allowedMethods("*").
                allowedHeaders("*").
                allowCredentials(true);
    }*/
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowedOrigins("https://9b52-196-203-207-178.ngrok-free.app") // Add your ngrok URL here
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
