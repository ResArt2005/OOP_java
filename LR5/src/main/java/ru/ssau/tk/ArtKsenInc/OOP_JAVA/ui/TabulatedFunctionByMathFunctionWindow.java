package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.DoubleNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.IntNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionScanner;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class TabulatedFunctionByMathFunctionWindow extends JDialog {
    private final JComboBox<String> functionComboBox;
    private final JTextField leftBoundField;
    private final JTextField rightBoundField;
    private final JTextField pointsCountField;
    private final Map<String, MathFunction> functionMap;
    private final TabulatedFunctionFactory factory;
    protected TabulatedFunction tabulatedFunction;

    public TabulatedFunctionByMathFunctionWindow(JFrame frame, TabulatedFunctionFactory factory) {
        super(frame, "Создание табулированной функции", true);
        this.factory = factory;
        this.functionMap = MathFunctionScanner.getAnnotatedFunctions(); // Используем динамическое сканирование
        setTitle("Создание табулированной функции");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Установка фона и шрифта для всего окна
        getContentPane().setBackground(ConstantColors.INDIGO);

        // Панель для ввода параметров
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.setBackground(ConstantColors.INDIGO);

        // Выпадающий список функций
        JLabel functionLabel = new JLabel("Выберите функцию:");
        functionLabel.setFont(ConstantFonts.Open_Sans_Bold);
        functionLabel.setForeground(ConstantColors.CYAN);
        functionComboBox = new JComboBox<>(functionMap.keySet().toArray(new String[0]));
        functionComboBox.setFont(ConstantFonts.Open_Sans_Bold);
        functionComboBox.setBackground(ConstantColors.FRENCH_VIOLET);
        functionComboBox.setForeground(ConstantColors.CYAN);

        // Поля для ввода интервалов и количества точек
        JLabel leftBoundLabel = new JLabel("Левая граница:");
        leftBoundLabel.setFont(ConstantFonts.Open_Sans_Bold);
        leftBoundLabel.setForeground(ConstantColors.CYAN);
        leftBoundField = new JTextField();
        leftBoundField.setFont(ConstantFonts.Open_Sans_Bold);
        leftBoundField.setBackground(ConstantColors.FRENCH_VIOLET);
        leftBoundField.setForeground(ConstantColors.CYAN);
        ((AbstractDocument) leftBoundField.getDocument()).setDocumentFilter(new DoubleNumericDocumentFilter());
        JLabel rightBoundLabel = new JLabel("Правая граница:");
        rightBoundLabel.setFont(ConstantFonts.Open_Sans_Bold);
        rightBoundLabel.setForeground(ConstantColors.CYAN);
        rightBoundField = new JTextField();
        rightBoundField.setFont(ConstantFonts.Open_Sans_Bold);
        rightBoundField.setBackground(ConstantColors.FRENCH_VIOLET);
        rightBoundField.setForeground(ConstantColors.CYAN);
        ((AbstractDocument) rightBoundField.getDocument()).setDocumentFilter(new DoubleNumericDocumentFilter());
        JLabel pointsCountLabel = new JLabel("Количество точек:");
        pointsCountLabel.setFont(ConstantFonts.Open_Sans_Bold);
        pointsCountLabel.setForeground(ConstantColors.CYAN);
        pointsCountField = new JTextField();
        pointsCountField.setFont(ConstantFonts.Open_Sans_Bold);
        pointsCountField.setBackground(ConstantColors.FRENCH_VIOLET);
        pointsCountField.setForeground(ConstantColors.CYAN);
        ((AbstractDocument) pointsCountField.getDocument()).setDocumentFilter(new IntNumericDocumentFilter());

        // Добавляем компоненты на панель
        inputPanel.add(functionLabel);
        inputPanel.add(functionComboBox);
        inputPanel.add(leftBoundLabel);
        inputPanel.add(leftBoundField);
        inputPanel.add(rightBoundLabel);
        inputPanel.add(rightBoundField);
        inputPanel.add(pointsCountLabel);
        inputPanel.add(pointsCountField);

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
                // Получаем выбранную функцию
                String selectedFunctionName = (String) functionComboBox.getSelectedItem();
                MathFunction selectedFunction = functionMap.get(selectedFunctionName);

                // Получаем введённые значения
                double leftX = Double.parseDouble(leftBoundField.getText());
                double rightX = Double.parseDouble(rightBoundField.getText());
                int pointsCount = Integer.parseInt(pointsCountField.getText());

                // Проверяем корректность ввода
                if (leftX >= rightX) {
                    throw new IllegalArgumentException("Левая граница должна быть меньше правой.");
                }
                if (pointsCount < 2) {
                    throw new IllegalArgumentException("Количество точек должно быть больше 1.");
                }
                double[] xValues = new double[pointsCount];
                double[] yValues = new double[pointsCount];
                double step = (rightX - leftX) / (pointsCount - 1);
                for (int i = 0; i < pointsCount; i++) {
                    xValues[i] = leftX + i * step;
                    yValues[i] = selectedFunction.apply(xValues[i]);
                }
                // Создаём табулированную функцию с помощью фабрики
                tabulatedFunction = factory.create(xValues, yValues);
                JOptionPane.showMessageDialog(TabulatedFunctionByMathFunctionWindow.this, "Функция успешно создана!");
                // Закрываем окно
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(TabulatedFunctionByMathFunctionWindow.this, "Некорректный ввод чисел.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(TabulatedFunctionByMathFunctionWindow.this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Метод для создания стилизованной кнопки
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(ConstantFonts.Open_Sans_Bold);
        button.setBackground(ConstantColors.FRENCH_VIOLET);
        button.setForeground(ConstantColors.CYAN);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Pointer при наведении
        return button;
    }

    public TabulatedFunction getTabulatedFunction() {
        return tabulatedFunction;
    }
}