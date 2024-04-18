package com.example.vitanovabackend.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    /*
    @Override
<<<<<<< HEAD:src/main/java/com/example/vitanovabackend/conf/WebMvcConfiguration.java
    public void addCorsMappings(CorsRegistry registry) {
        /*registry.addMapping("/*").
=======
    /*public void addCorsMappings(CorsRegistry registry) {
     /*   registry.addMapping("/*").
>>>>>>> yoser:src/main/java/com/example/vitanovabackend/WebMvcConfiguration.java
                allowedOrigins("*").
                allowedMethods("*").
                allowedHeaders("*").
                allowCredentials(true);
<<<<<<< HEAD:src/main/java/com/example/vitanovabackend/conf/WebMvcConfiguration.java
<<<<<<< HEAD:src/main/java/com/example/vitanovabackend/conf/WebMvcConfiguration.java
   */


    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowedOrigins("https://9b52-196-203-207-178.ngrok-free.app") // Add your ngrok URL here
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}

