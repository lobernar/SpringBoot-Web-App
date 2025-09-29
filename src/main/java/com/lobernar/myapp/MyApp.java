package com.lobernar.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;;

@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        // Load .env before Spring context starts
        Dotenv dotenv = Dotenv.load();
        // Pass .env values to Spring as system properties
        System.setProperty("app.jwt.secret", dotenv.get("JWT_SECRET"));
        System.setProperty("spring.datasource.url", dotenv.get("PSQLDB_URL"));
        System.setProperty("app.jwt.expiration", dotenv.get("JWT_EXPIRATION"));
        SpringApplication.run(MyApp.class, args);
    }   
}