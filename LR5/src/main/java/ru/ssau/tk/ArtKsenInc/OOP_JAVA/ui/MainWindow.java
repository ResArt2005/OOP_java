package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows.SettingsWindowChooseFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame{
    private final int WIDTH_WINDOW = 600; // Ширина окна
    private final int HEIGHT_WINDOW = 400; // Высота окна
    private TabulatedFunctionOperationService factoryService; // Для хранения текущей фабрики
    private SettingsWindowChooseFactory settingsWindow; // Окно настроек
    public MainWindow() {
        // Установка заголовка окна
        setTitle("Главное окно программы");
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Менеджер фабрики (по умолчанию - фабрика на основе массива)
        factoryService = new TabulatedFunctionOperationService();

        // Установка компоновки
        setLayout(new FlowLayout());

        // Кнопка для открытия окна настроек
        JButton settingsButton = new JButton("Открыть настройки");
        settingsButton.addActionListener(_ -> openSettingsWindow());
         // Панель с кнопками
        JPanel buttonPanel = new JPanel();
        JButton operationsButton = new JButton("Операции с функциями");
        // Добавляем кнопку для открытия окна операций
        operationsButton.addActionListener(e -> openTabulatedFunctionOperationsWindow());

        buttonPanel.add(operationsButton);
        add(buttonPanel, BorderLayout.CENTER);
        // Добавляем кнопки в главное окно
        add(settingsButton);
        // Обработчик закрытия главного окна (завершает программу)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // Завершить выполнение программы при закрытии главного окна
            }
        });

        // Показываем главное окно
        setVisible(true);
    }

    // Метод для открытия окна настроек
    private void openSettingsWindow() {
        if (settingsWindow == null || !settingsWindow.isShowing()) {
            settingsWindow = new SettingsWindowChooseFactory(this, factoryService);
            settingsWindow.setVisible(true);
        }
    }

    // Метод для открытия окна операций с табулированными функциями
    private void openTabulatedFunctionOperationsWindow() {
        new TabulatedFunctionOperationsWindow(this, factoryService);
    }
    public static void main(String[] args) {
        // Создание и отображение главного окна
        SwingUtilities.invokeLater(MainWindow::new);
    }
}