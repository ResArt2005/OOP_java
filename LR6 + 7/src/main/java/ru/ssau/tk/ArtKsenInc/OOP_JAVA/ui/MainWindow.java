package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.UserDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedDifferentialOperator;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.RoundedLabel;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows.SettingsWindowChooseFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class MainWindow extends JFrame {
    private final int WIDTH_WINDOW = 800; // Ширина окна
    private final int HEIGHT_WINDOW = 600; // Высота окна
    private TabulatedFunctionOperationService factoryService; // Для хранения текущей фабрики
    private SettingsWindowChooseFactory settingsWindow; // Окно настроек

    public MainWindow(UserDTO userDTO) {
        // Установка заголовка окна
        setTitle("Главное окно программы");
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Менеджер фабрики (по умолчанию - фабрика на основе массива)
        factoryService = new TabulatedFunctionOperationService();

        // Установка кастомной компоновки
        setLayout(new BorderLayout());

        // Устанавливаем цвет фона окна
        getContentPane().setBackground(ConstantColors.RICH_PURPLE);

        // Панель для кнопок с центральным размещением
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10));  // Один столбец с отступами
        buttonPanel.setBackground(ConstantColors.RICH_PURPLE);

        // Кнопка для открытия окна настроек
        JButton settingsButton = createRoundedButton("Открыть настройки", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.THISTLE, new Cursor(Cursor.HAND_CURSOR));
        settingsButton.addActionListener(e -> openSettingsWindow());

        // Кнопка для операций с функциями
        JButton operationsButton = createRoundedButton("Элементарные операции с функциями", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.THISTLE, new Cursor(Cursor.HAND_CURSOR));
        operationsButton.addActionListener(e -> openTabulatedFunctionOperationsWindow());

        // Кнопка для операции дифференцирования
        JButton differentialOperation = createRoundedButton("Операция дифференцирования над функцией", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.THISTLE, new Cursor(Cursor.HAND_CURSOR));
        differentialOperation.addActionListener(e -> TabulatedFunctionDifferentialOperationsWindow());

        JButton TBEditor = createRoundedButton("Редактор табулированной функции", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.THISTLE, new Cursor(Cursor.HAND_CURSOR));
        TBEditor.addActionListener(e -> FunctionEditorWindow());

        // Кнопка для перехода в окно вычисления интеграла
        JButton integralOperationButton = createRoundedButton("Вычисление интеграла", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.THISTLE, new Cursor(Cursor.HAND_CURSOR));
        integralOperationButton.addActionListener(e -> openTabulatedFunctionIntegralOperationsWindow());

        // Кнопка для перехода в окно администрирования
        JButton adminButton = createRoundedButton("ТОЛЬКО ДЛЯ ПЕРСОНАЛА", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.THISTLE, new Cursor(Cursor.HAND_CURSOR));
        adminButton.addActionListener(e -> openWorkWithDBWindow());

        // Добавляем кнопки в панель
        buttonPanel.add(settingsButton);
        buttonPanel.add(operationsButton);
        buttonPanel.add(differentialOperation);
        buttonPanel.add(TBEditor);
        buttonPanel.add(integralOperationButton);
        if (Objects.equals(userDTO.getToken(), "admin")) {
            buttonPanel.add(adminButton);
        }

        // Добавляем панель кнопок в главное окно
        add(buttonPanel, BorderLayout.CENTER);

        // Создание и добавление заголовка с закругленными краями
        RoundedLabel headerLabel = new RoundedLabel("Главное меню", 20, ConstantColors.RED_VIOLET);
        headerLabel.setBackground(ConstantColors.DARK_LILAC);
        headerLabel.setForeground(ConstantColors.RED_VIOLET);
        headerLabel.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(24f)); // Увеличенный шрифт для заголовка
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(WIDTH_WINDOW, 50));
        headerLabel.setBorder(BorderFactory.createLineBorder(ConstantColors.DARK_VIOLET, 2)); // Добавляем темно-бордовую обводку рамки
        add(headerLabel, BorderLayout.NORTH);

        // Обработчик закрытия главного окна (завершает программу)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // Завершить выполнение программы при закрытии главного окна
            }
        });

        // Центрирование окна
        setLocationRelativeTo(null);

        // Показываем главное окно
        setVisible(true);
    }

    // Метод для создания кнопки с закругленными краями
    private JButton createRoundedButton(String text, Font font, Color background, Color foreground, Cursor cursor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setCursor(cursor);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ConstantColors.DARK_VIOLET, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        button.setContentAreaFilled(false);
        return button;
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

    // Метод для открытия окна операций дифференцирования
    private void TabulatedFunctionDifferentialOperationsWindow() {
        new TabulatedFunctionDifferentialOperationsWindow(this, new TabulatedDifferentialOperator(factoryService.getFactory()));
    }

    // Метод для открытия окна операций дифференцирования
    private void FunctionEditorWindow() {
        new FunctionEditorWindow(this, factoryService);
    }

    // Метод для открытия окна вычисления интеграла
    private void openTabulatedFunctionIntegralOperationsWindow() {
        new TabulatedFunctionIntegralOperationsWindow(this, new TabulatedFunctionOperationService());
    }

    private void openWorkWithDBWindow() {
        new WorkWithDBWindow(this);
    }
}
