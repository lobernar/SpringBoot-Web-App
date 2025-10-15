package com.lobernar.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;;

/**
 * Main entry point for the Spring Boot application.
 * Loads environment variables from .env and sets them as system properties before starting Spring.
 */

@SpringBootApplication
public class MyApp {
    /**
     * Loads .env variables and starts the Spring Boot application.
     * Sets JWT secret, expiration, and datasource URL as system properties for Spring to use.
     * @param args command-line arguments
     */
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