package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ButtonsDesign;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SettingsWindowChooseFactory extends JDialog {

    public SettingsWindowChooseFactory(JFrame owner, TabulatedFunctionOperationService factoryService) {
        super(owner, "Настройки", true); // Модальное окно

        setSize(500, 200);
        setLocationRelativeTo(owner);

        // Устанавливаем фон диалогового окна
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Радио-кнопки для выбора фабрики
        JRadioButton arrayFactoryButton = createStyledRadioButton(
                "Фабрика на основе массива",
                factoryService.getFactory() instanceof ArrayTabulatedFunctionFactory
        );
        JRadioButton listFactoryButton = createStyledRadioButton(
                "Фабрика на основе связного списка",
                factoryService.getFactory() instanceof LinkedListTabulatedFunctionFactory
        );

        // Группируем радио-кнопки
        ButtonGroup group = new ButtonGroup();
        group.add(arrayFactoryButton);
        group.add(listFactoryButton);

        // Кнопка для сохранения выбора
        JButton saveButton = ButtonsDesign.createStyledButton("Сохранить", ConstantFonts.Open_Sans_Bold, ConstantColors.DARK_BLUE, ConstantColors.TIFFANY_BLUE, new Cursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(_ -> {
            if (arrayFactoryButton.isSelected()) {
                factoryService.setFactory(new ArrayTabulatedFunctionFactory());
            } else if (listFactoryButton.isSelected()) {
                factoryService.setFactory(new LinkedListTabulatedFunctionFactory());
            }
            // Закрыть окно после сохранения
            dispose();
        });

        // Панель для элементов
        JPanel panel = new JPanel();
        panel.setBackground(ConstantColors.DARK_PURPLE);
        panel.setLayout(new GridLayout(3, 1, 10, 10)); // Сетка 3 на 1 с отступами

        panel.add(arrayFactoryButton);
        panel.add(listFactoryButton);
        panel.add(saveButton);

        add(panel);

        // Обработчик закрытия окна (освобождает ссылку на окно)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    // Метод для создания стилизованных радио-кнопок
    private JRadioButton createStyledRadioButton(String text, boolean isSelected) {
        JRadioButton radioButton = new JRadioButton(text, isSelected);
        radioButton.setFont(ConstantFonts.Open_Sans_Bold); // Шрифт Open Sans Bold
        radioButton.setForeground(ConstantColors.TIFFANY_BLUE); // Голубой цвет текста
        radioButton.setBackground(ConstantColors.DARK_PURPLE); // Фон как у окна
        radioButton.setFocusPainted(false); // Убираем обводку при фокусе
        return radioButton;
    }
}