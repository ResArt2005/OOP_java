package ru.ssau.tk.ArtKsenInc.OOP_JAVA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.ChooseUserWindow;

import javax.swing.*;

@SpringBootApplication
@ComponentScan
public class Main {
    public static void main(String[] args) {
        // Открытие окна после запуска приложения
        SwingUtilities.invokeLater(ChooseUserWindow::new);
        // Запуск Spring Boot приложения
        SpringApplication.run(Main.class, args);
    }
}