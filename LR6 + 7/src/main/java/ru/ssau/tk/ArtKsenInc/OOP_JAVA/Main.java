package ru.ssau.tk.ArtKsenInc.OOP_JAVA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.MainWindow;

import javax.swing.*;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
        SpringApplication.run(Main.class, args);
    }
}