package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.CompositeFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionScanner;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.IntNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class CompositeFunctionCreationWindow extends JDialog {
    private final Map<String, MathFunction> functionMap;
    private final JTextField functionNameField;
    private final JTextField functionsCountField;
    private CompositeFunction compositeFunction;

    public CompositeFunctionCreationWindow(JFrame frame) {
        super(frame, "Создание сложной функции", true);
        this.functionMap = MathFunctionScanner.getAnnotatedFunctions(); // Используем динамическое сканирование
        this.functionMap.putAll(dbTools.getAllMathFunctionsAsNameAndMF());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Установка фона и шрифта для всего окна
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Панель для ввода параметров
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.setBackground(ConstantColors.DARK_PURPLE);

        // Поле для ввода количества функций
        JLabel functionsCountLabel = new JLabel("Количество функций:");
        functionsCountLabel.setFont(ConstantFonts.Open_Sans_Bold);
        functionsCountLabel.setForeground(ConstantColors.THISTLE);
        functionsCountField = new JTextField(10);
        functionsCountField.setFont(ConstantFonts.Open_Sans_Bold);
        functionsCountField.setBackground(ConstantColors.RICH_PURPLE);
        functionsCountField.setForeground(ConstantColors.THISTLE);
        ((AbstractDocument) functionsCountField.getDocument()).setDocumentFilter(new IntNumericDocumentFilter());
        // Поле для ввода названия функции
        JLabel functionNameLabel = new JLabel("Название функции:");
        functionNameLabel.setFont(ConstantFonts.Open_Sans_Bold);
        functionNameLabel.setForeground(ConstantColors.THISTLE);
        functionNameField = new JTextField(10);
        functionNameField.setFont(ConstantFonts.Open_Sans_Bold);
        functionNameField.setBackground(ConstantColors.RICH_PURPLE);
        functionNameField.setForeground(ConstantColors.THISTLE);

        // Добавляем компоненты на панель
        inputPanel.add(functionsCountLabel);
        inputPanel.add(functionsCountField);
        inputPanel.add(functionNameLabel);
        inputPanel.add(functionNameField);

        // Кнопка создания функции
        JButton createButton = createStyledButton("Создать");
        createButton.addActionListener(new CreateFunctionListener());

        // Добавляем панели на окно
        add(inputPanel, BorderLayout.CENTER);
        add(createButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    // Обработчик для кнопки создания функции
    private class CreateFunctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Получаем введённое количество функций
                int functionsCount = Integer.parseInt(functionsCountField.getText());
                if (functionsCount <= 1) {
                    throw new IllegalArgumentException("Количество функций должно быть больше 1.");
                }

                // Получаем введённое название функции
                String functionName = functionNameField.getText();
                if (functionName.isEmpty()) {
                    throw new IllegalArgumentException("Название функции не может быть пустым.");
                }

                // Создаём панель для выбора функций
                JPanel selectionPanel = new JPanel(new GridLayout(functionsCount, 2));
                selectionPanel.setBackground(ConstantColors.DARK_PURPLE);
                JComboBox<String>[] functionComboBoxes = new JComboBox[functionsCount];

                for (int i = 0; i < functionsCount; i++) {
                    JLabel label = new JLabel("Функция " + (i + 1) + ":");
                    label.setFont(ConstantFonts.Open_Sans_Bold);
                    label.setForeground(ConstantColors.THISTLE);
                    JComboBox<String> comboBox = new JComboBox<>(functionMap.keySet().toArray(new String[0]));
                    comboBox.setFont(ConstantFonts.Open_Sans_Bold);
                    comboBox.setBackground(ConstantColors.RICH_PURPLE);
                    comboBox.setForeground(ConstantColors.THISTLE);
                    functionComboBoxes[i] = comboBox;
                    selectionPanel.add(label);
                    selectionPanel.add(comboBox);
                }

                // Показываем диалоговое окно для выбора функций
                int result = JOptionPane.showConfirmDialog(CompositeFunctionCreationWindow.this, selectionPanel, "Выберите функции", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    MathFunction[] selectedFunctions = new MathFunction[functionsCount];
                    for (int i = 0; i < functionsCount; i++) {
                        String selectedFunctionName = (String) functionComboBoxes[i].getSelectedItem();
                        selectedFunctions[i] = functionMap.get(selectedFunctionName);
                    }

                    // Создаём сложную функцию
                    MathFunction composite = selectedFunctions[0];
                    for (int i = 1; i < functionsCount - 1; i++) {
                        composite = composite.andThen(selectedFunctions[i]);
                    }
                    compositeFunction = new CompositeFunction(composite, selectedFunctions[functionsCount - 1]);
                    dbTools.createMathFunction(functionName, compositeFunction);
                    dbTools.createLog("Создана математическая функция " + functionName);
                    JOptionPane.showMessageDialog(CompositeFunctionCreationWindow.this, "Функция успешно создана и сохранена!");
                    // Закрываем окно
                    dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CompositeFunctionCreationWindow.this, "Некорректный ввод числа функций", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(CompositeFunctionCreationWindow.this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
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
