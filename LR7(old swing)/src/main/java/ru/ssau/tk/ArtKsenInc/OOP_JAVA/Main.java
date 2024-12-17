package ru.ssau.tk.ArtKsenInc.OOP_JAVA;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Создание и отображение главного окна
        SwingUtilities.invokeLater(MainWindow::new);
    }
}