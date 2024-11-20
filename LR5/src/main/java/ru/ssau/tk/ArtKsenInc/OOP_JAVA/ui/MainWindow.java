package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedDifferentialOperator;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ButtonsDesign;
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
        getContentPane().setBackground(ConstantColors.FRENCH_VIOLET);

        // Панель для кнопок с центральным размещением
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));  // Сетка 3 на 1 с отступами
        buttonPanel.setBackground(ConstantColors.FRENCH_VIOLET);

        // Кнопка для открытия окна настроек
        JButton settingsButton = ButtonsDesign.createStyledButton("Открыть настройки", ConstantFonts.Open_Sans_Bold, ConstantColors.FRENCH_VIOLET, ConstantColors.TIFFANY_BLUE, new Cursor(Cursor.HAND_CURSOR));
        settingsButton.addActionListener(_ -> openSettingsWindow());

        // Кнопка для операций с функциями
        JButton operationsButton = ButtonsDesign.createStyledButton("Элементарные операции с функциями", ConstantFonts.Open_Sans_Bold, ConstantColors.FRENCH_VIOLET, ConstantColors.TIFFANY_BLUE, new Cursor(Cursor.HAND_CURSOR));
        operationsButton.addActionListener(_ -> openTabulatedFunctionOperationsWindow());

        // Кнопка для операции дифференцирования
        JButton differentialOperation = ButtonsDesign.createStyledButton("Операция дифференцирования над функцией", ConstantFonts.Open_Sans_Bold, ConstantColors.FRENCH_VIOLET, ConstantColors.TIFFANY_BLUE, new Cursor(Cursor.HAND_CURSOR));
        differentialOperation.addActionListener(_ -> TabulatedFunctionDifferentialOperationsWindow());

        JButton TBEditor = ButtonsDesign.createStyledButton("Редактор табулированной функции", ConstantFonts.Open_Sans_Bold, ConstantColors.FRENCH_VIOLET, ConstantColors.TIFFANY_BLUE, new Cursor(Cursor.HAND_CURSOR));
        TBEditor.addActionListener(_ -> FunctionEditorWindow());


        // Кнопка для перехода в окно вычисления интеграла
        JButton integralOperationButton = ButtonsDesign.createStyledButton("Вычисление интеграла", ConstantFonts.Open_Sans_Bold, ConstantColors.FRENCH_VIOLET, ConstantColors.TIFFANY_BLUE, new Cursor(Cursor.HAND_CURSOR));
        integralOperationButton.addActionListener(_ -> openTabulatedFunctionIntegralOperationsWindow());

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
        headerLabel.setBackground(ConstantColors.FRENCH_VIOLET);
        headerLabel.setForeground(ConstantColors.TIFFANY_BLUE);
        headerLabel.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(24f)); // Увеличенный шрифт для заголовка
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(WIDTH_WINDOW, 50));
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

    public static void main(String[] args) {
        // Создание и отображение главного окна
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
