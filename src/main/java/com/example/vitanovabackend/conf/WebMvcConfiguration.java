package com.example.vitanovabackend.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    /*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /*registry.addMapping("/*").
                allowedOrigins("*").
                allowedMethods("*").
                allowedHeaders("*").
                allowCredentials(true);
<<<<<<< HEAD:src/main/java/com/example/vitanovabackend/conf/WebMvcConfiguration.java
   */ }


