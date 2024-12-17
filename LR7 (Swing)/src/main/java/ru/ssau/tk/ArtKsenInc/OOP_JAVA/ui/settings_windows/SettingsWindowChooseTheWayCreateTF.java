package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.TabulatedFunctionByArraysWindow;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.TabulatedFunctionByMathFunctionWindow;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;

import javax.swing.*;
import java.awt.*;

public class SettingsWindowChooseTheWayCreateTF extends JDialog {
    private TabulatedFunction function;
    private final TabulatedFunctionOperationService factoryService;
    private JFrame owner;

    public SettingsWindowChooseTheWayCreateTF(JFrame owner, TabulatedFunctionOperationService factoryService) {
        super(owner, "Создание табулированной функции", true);  // Модальное окно
        this.owner = owner;
        this.factoryService = factoryService;
        setSize(500, 150);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Установка фона и шрифта для всего окна
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Кнопка для открытия окна TabulatedFunctionByArraysWindow
        JButton arrayFactoryButton = createStyledButton("Создать функцию по массивам");
        arrayFactoryButton.addActionListener(e -> openTabulatedFunctionByArraysWindow());

        // Кнопка для открытия окна TabulatedFunctionByMathFunctionWindow
        JButton listFactoryButton = createStyledButton("Создать функцию по математической функции");
        listFactoryButton.addActionListener(e -> openTabulatedFunctionByMathFunctionWindow());

        JPanel panel = new JPanel();
        panel.setBackground(ConstantColors.DARK_PURPLE);
        panel.add(arrayFactoryButton);
        panel.add(listFactoryButton);

        add(panel, BorderLayout.CENTER);
    }

    // Метод для открытия окна TabulatedFunctionByArraysWindow
    private void openTabulatedFunctionByArraysWindow() {
        TabulatedFunctionByArraysWindow arraysWindow = new TabulatedFunctionByArraysWindow(owner, factoryService.getFactory());
        //arraysWindow.setVisible(true);
        function = arraysWindow.getTabulatedFunction(); // Получаем функцию после закрытия окна
        dispose(); // Закрываем текущее окно после выбора
    }

    // Метод для открытия окна TabulatedFunctionByMathFunctionWindow
    private void openTabulatedFunctionByMathFunctionWindow() {
        TabulatedFunctionByMathFunctionWindow mathWindow = new TabulatedFunctionByMathFunctionWindow(owner, factoryService.getFactory());
        //mathWindow.setVisible(true);
        function = mathWindow.getTabulatedFunction(); // Получаем функцию после закрытия окна
        dispose(); // Закрываем текущее окно после выбора
    }

    public TabulatedFunction getTabulatedFunction() {
        return function;  // Возвращаем выбранную или созданную функцию
    }

    // Метод для создания стилизованной кнопки
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(ConstantFonts.Open_Sans_Bold);
        button.setBackground(ConstantColors.RICH_PURPLE);
        button.setForeground(ConstantColors.THISTLE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Pointer при наведении
        return button;
    }
}
