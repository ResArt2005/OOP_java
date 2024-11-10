package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.myOwnFunctionsForEquation.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.DoubleNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.IntNumericDocumentFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

public class TabulatedFunctionByMathFunctionWindow extends JFrame {
    private final JComboBox<String> functionComboBox;
    private final JTextField leftBoundField;
    private final JTextField rightBoundField;
    private final JTextField pointsCountField;
    private final Map<String, MathFunction> functionMap;
    final int PANEL_ROWS = 5;
    final int PANEL_COLUMNS = 2;
    final int WIDTH_WINDOW = 600; //Ширина окна
    final int HEIGHT_WINDOW = 400; //Высота окна
    private final LinkedListTabulatedFunctionFactory factory;
    private TabulatedFunction tabulatedFunction;
    JFrame frame = new JFrame();

    public TabulatedFunctionByMathFunctionWindow() {
        factory = new LinkedListTabulatedFunctionFactory();
        this.functionMap = createFunctionMap();
        frame.setTitle("Создание табулированной функции");
        frame.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Панель для ввода параметров
        JPanel inputPanel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));

        // Выпадающий список функций
        JLabel functionLabel = new JLabel("Выберите функцию:");
        functionComboBox = new JComboBox<>(functionMap.keySet().toArray(new String[0]));

        // Поля для ввода интервалов и количества точек
        JLabel leftBoundLabel = new JLabel("Левая граница:");
        leftBoundField = new JTextField();
        ((AbstractDocument) leftBoundField.getDocument()).setDocumentFilter(new DoubleNumericDocumentFilter());
        JLabel rightBoundLabel = new JLabel("Правая граница:");
        rightBoundField = new JTextField();
        ((AbstractDocument) rightBoundField.getDocument()).setDocumentFilter(new DoubleNumericDocumentFilter());
        JLabel pointsCountLabel = new JLabel("Количество точек:");
        pointsCountField = new JTextField();
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
        JButton createButton = new JButton("Создать");
        createButton.addActionListener(new CreateFunctionListener());

        // Добавляем панели на окно
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(createButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Метод для создания отображения функций
    private Map<String, MathFunction> createFunctionMap() {
        Map<String, MathFunction> map = new TreeMap<>(); // TreeMap для сортировки по ключу
        map.put("Квадратичная функция", new SqrFunction());
        map.put("Тождественная функция", new IdentityFunction());
        map.put("Функция возведения в квадрат", new SquareFunction());
        map.put("Функция константы 0", new ZeroFunction());
        map.put("Функция константы 1", new UnitFunction());
        map.put("Синус", new SinFunction());
        map.put("Косинус", new CosFunction());
        map.put("Тангенс", new TgFunction());
        map.put("Котангенс", new CtgFunction());
        map.put("Экспонента", new ExpFunction());
        return map;
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
                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(TabulatedFunctionByMathFunctionWindow.this, "Некорректный ввод чисел.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(TabulatedFunctionByMathFunctionWindow.this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TabulatedFunctionByMathFunctionWindow::new);
    }
}