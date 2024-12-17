package ru.ssau.tk.ArtKsenInc.OOP_JAVA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class Main {
    public static void main(String[] args) {
        // Запуск Spring Boot приложения
        SpringApplication.run(Main.class, args);
    }
}