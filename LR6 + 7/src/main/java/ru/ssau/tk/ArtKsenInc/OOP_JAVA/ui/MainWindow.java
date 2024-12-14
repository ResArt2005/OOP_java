package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

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

public class MainWindow extends JFrame {
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

        // Установка кастомной компоновки
        setLayout(new BorderLayout());

        // Устанавливаем цвет фона окна
        getContentPane().setBackground(ConstantColors.RICH_PURPLE);

        // Панель для кнопок с центральным размещением
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));  // Сетка 5 на 1 с отступами
        buttonPanel.setBackground(ConstantColors.RICH_PURPLE);

        // Кнопка для открытия окна настроек
        JButton settingsButton = createRoundedButton("Открыть настройки", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.TIFFANY_BLUE, new Cursor(Cursor.HAND_CURSOR));
        settingsButton.addActionListener(e -> openSettingsWindow());

        // Кнопка для операций с функциями
        JButton operationsButton = createRoundedButton("Элементарные операции с функциями", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.TIFFANY_BLUE, new Cursor(Cursor.HAND_CURSOR));
        operationsButton.addActionListener(e -> openTabulatedFunctionOperationsWindow());

        // Кнопка для операции дифференцирования
        JButton differentialOperation = createRoundedButton("Операция дифференцирования над функцией", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.TIFFANY_BLUE, new Cursor(Cursor.HAND_CURSOR));
        differentialOperation.addActionListener(e -> TabulatedFunctionDifferentialOperationsWindow());

        JButton TBEditor = createRoundedButton("Редактор табулированной функции", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.TIFFANY_BLUE, new Cursor(Cursor.HAND_CURSOR));
        TBEditor.addActionListener(e -> FunctionEditorWindow());

        // Кнопка для перехода в окно вычисления интеграла
        JButton integralOperationButton = createRoundedButton("Вычисление интеграла", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.TIFFANY_BLUE, new Cursor(Cursor.HAND_CURSOR));
        integralOperationButton.addActionListener(e -> openTabulatedFunctionIntegralOperationsWindow());

        // Добавляем кнопки в панель
        buttonPanel.add(settingsButton);
        buttonPanel.add(operationsButton);
        buttonPanel.add(differentialOperation);
        buttonPanel.add(TBEditor);
        buttonPanel.add(integralOperationButton);

        // Добавляем панель кнопок в главное окно
        add(buttonPanel, BorderLayout.CENTER);

        // Создание и добавление заголовка с закругленными краями
        RoundedLabel headerLabel = new RoundedLabel("Главное меню", 20);
        headerLabel.setBackground(ConstantColors.DARK_BLUE);
        headerLabel.setForeground(ConstantColors.TIFFANY_BLUE);
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

        // Показываем главное окно
        setVisible(true);
    }

    // Метод для создания кнопки с краями
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
}