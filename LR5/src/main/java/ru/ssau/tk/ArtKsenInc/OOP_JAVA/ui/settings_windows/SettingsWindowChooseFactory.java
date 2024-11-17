package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SettingsWindowChooseFactory extends JDialog {

    public SettingsWindowChooseFactory(JFrame owner, TabulatedFunctionOperationService factoryService) {
        super(owner, "Настройки", true); // Модальное окно

        setSize(300, 200);
        setLocationRelativeTo(owner);

        // Радио-кнопки для выбора фабрики
        JRadioButton arrayFactoryButton = new JRadioButton("Фабрика на основе массива", factoryService.getFactory() instanceof ArrayTabulatedFunctionFactory);
        JRadioButton listFactoryButton = new JRadioButton("Фабрика на основе связного списка", factoryService.getFactory() instanceof LinkedListTabulatedFunctionFactory);

        // Группируем радио-кнопки
        ButtonGroup group = new ButtonGroup();
        group.add(arrayFactoryButton);
        group.add(listFactoryButton);

        // Кнопка для сохранения выбора
        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(_ -> {
            if (arrayFactoryButton.isSelected()) {
                factoryService.setFactory(new ArrayTabulatedFunctionFactory());
            } else if (listFactoryButton.isSelected()) {
                factoryService.setFactory(new LinkedListTabulatedFunctionFactory());
            }
            // Закрыть окно после сохранения
            dispose();
        });

        // Добавляем элементы на форму
        JPanel panel = new JPanel();
        panel.add(arrayFactoryButton);
        panel.add(listFactoryButton);
        panel.add(saveButton);

        add(panel);

        // Обработчик закрытия окна (освобождает ссылку на окно)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Закрыть окно
                dispose();
            }
        });
    }
}
