package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.DoubleNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.IntNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionScanner;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ButtonsDesign;

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
    JFrame owner;

    public TabulatedFunctionByMathFunctionWindow(JFrame frame, TabulatedFunctionFactory factory) {
        super(frame, "Создание табулированной функции", true);
        owner = frame;
        this.factory = factory;
        this.functionMap = MathFunctionScanner.getAnnotatedFunctions(); // Используем динамическое сканирование
        setTitle("Создание табулированной функции");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Установка фона и шрифта для всего окна
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Панель для ввода параметров
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.setBackground(ConstantColors.DARK_PURPLE);
        JButton compositeFunctionButton = ButtonsDesign.createStyledButton("Создать сложную функцию", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.TIFFANY_BLUE, new Cursor(Cursor.HAND_CURSOR));
        compositeFunctionButton.addActionListener(_ -> openCompositeFunctionCreationWindow());
        // Выпадающий список функций
        JLabel functionLabel = new JLabel("Выберите функцию:");
        functionLabel.setFont(ConstantFonts.Open_Sans_Bold);
        functionLabel.setForeground(ConstantColors.TIFFANY_BLUE);
        functionComboBox = new JComboBox<>(functionMap.keySet().toArray(new String[0]));
        functionComboBox.setFont(ConstantFonts.Open_Sans_Bold);
        functionComboBox.setBackground(ConstantColors.RICH_PURPLE);
        functionComboBox.setForeground(ConstantColors.TIFFANY_BLUE);

        // Поля для ввода интервалов и количества точек
        JLabel leftBoundLabel = new JLabel("Левая граница:");
        leftBoundLabel.setFont(ConstantFonts.Open_Sans_Bold);
        leftBoundLabel.setForeground(ConstantColors.TIFFANY_BLUE);
        leftBoundField = new JTextField();
        leftBoundField.setFont(ConstantFonts.Open_Sans_Bold);
        leftBoundField.setBackground(ConstantColors.RICH_PURPLE);
        leftBoundField.setForeground(ConstantColors.TIFFANY_BLUE);
        ((AbstractDocument) leftBoundField.getDocument()).setDocumentFilter(new DoubleNumericDocumentFilter());
        JLabel rightBoundLabel = new JLabel("Правая граница:");
        rightBoundLabel.setFont(ConstantFonts.Open_Sans_Bold);
        rightBoundLabel.setForeground(ConstantColors.TIFFANY_BLUE);
        rightBoundField = new JTextField();
        rightBoundField.setFont(ConstantFonts.Open_Sans_Bold);
        rightBoundField.setBackground(ConstantColors.RICH_PURPLE);
        rightBoundField.setForeground(ConstantColors.TIFFANY_BLUE);
        ((AbstractDocument) rightBoundField.getDocument()).setDocumentFilter(new DoubleNumericDocumentFilter());
        JLabel pointsCountLabel = new JLabel("Количество точек:");
        pointsCountLabel.setFont(ConstantFonts.Open_Sans_Bold);
        pointsCountLabel.setForeground(ConstantColors.TIFFANY_BLUE);
        pointsCountField = new JTextField();
        pointsCountField.setFont(ConstantFonts.Open_Sans_Bold);
        pointsCountField.setBackground(ConstantColors.RICH_PURPLE);
        pointsCountField.setForeground(ConstantColors.TIFFANY_BLUE);
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
        add(compositeFunctionButton, BorderLayout.NORTH);
        setVisible(true);

        // Загружаем созданные сложные функции
        //loadCompositeFunctions();
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
        button.setBackground(ConstantColors.RICH_PURPLE);
        button.setForeground(ConstantColors.TIFFANY_BLUE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Pointer при наведении
        return button;
    }

    public TabulatedFunction getTabulatedFunction() {
        return tabulatedFunction;
    }

    // Метод для загрузки созданных сложных функций
    /*private void loadCompositeFunctions() {
        try {
            Map<String, CompositeFunction> compositeFunctions = CompositeFunctionStorage.loadCompositeFunctions();
            for (Map.Entry<String, CompositeFunction> entry : compositeFunctions.entrySet()) {
                functionMap.put(entry.getKey(), entry.getValue());
                functionComboBox.addItem(entry.getKey());
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки сложных функций: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
    */
    // Метод для открытия окна создания сложной функции
    private void openCompositeFunctionCreationWindow() {
        new CompositeFunctionCreationWindow(owner);
    }
}
