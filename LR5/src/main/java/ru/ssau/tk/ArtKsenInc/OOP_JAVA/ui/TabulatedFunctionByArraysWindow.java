package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.exceptions.ArrayIsNotSortedException;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.IntNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.NumericCellEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class TabulatedFunctionByArraysWindow extends JFrame {
    private final JTextField pointCountField;
    private final JTable table;
    private final DefaultTableModel tableModel;
    final int FIELD_COLUMNS = 5;  // Количество видимых символов
    final int WIDTH_WINDOW = 600; //Ширина окна
    final int HEIGHT_WINDOW = 400; //Высота окна
    private final LinkedListTabulatedFunctionFactory factory;
    private TabulatedFunction tabulatedFunction;
    JFrame frame = new JFrame();

    public TabulatedFunctionByArraysWindow() {
        factory = new LinkedListTabulatedFunctionFactory();
        frame.setTitle("Создание табулированной функции");
        frame.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Панель ввода количества точек
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel pointCountLabel = new JLabel("Количество точек:");
        pointCountField = new JTextField(FIELD_COLUMNS);
        ((AbstractDocument) pointCountField.getDocument()).setDocumentFilter(new IntNumericDocumentFilter());
        JButton createTableButton = new JButton("Создать таблицу");

        inputPanel.add(pointCountLabel);
        inputPanel.add(pointCountField);
        inputPanel.add(createTableButton);
        // Панель для таблицы
        JPanel tablePanel = new JPanel();
        tableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);
        table = new JTable(tableModel);
        // Добавляем редактор для ввода только чисел
        table.setDefaultEditor(Object.class, new NumericCellEditor());
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        // Кнопка для создания табулированной функции
        JButton createFunctionButton = new JButton("Создать функцию");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createFunctionButton);

        // Добавление компонентов на главное окно
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Действие на кнопку создания таблицы
        createTableButton.addActionListener(_ -> createTable());

        // Действие на кнопку создания функции
        createFunctionButton.addActionListener(_ -> createTabulatedFunction());
        frame.setVisible(true);
    }

    // Метод для создания таблицы на основе количества точек
    private void createTable() {
        int pointCount;
        try {
            pointCount = Integer.parseInt(pointCountField.getText());
            if (pointCount < 2) {
                JOptionPane.showMessageDialog(this, "Введите корректное количество точек!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Введите корректное количество точек!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.setRowCount(0); // Очищаем старые строки
        for (int i = 0; i < pointCount; i++) {
            tableModel.addRow(new Object[]{"", ""});
        }
    }

    // Метод для создания табулированной функции из таблицы
    private void createTabulatedFunction() {
        //Завершает редактирование ячейки пользователем
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }
        int rowCount = tableModel.getRowCount();
        double[] xValues = new double[rowCount];
        double[] yValues = new double[rowCount];
        try {
            for (int i = 0; i < rowCount; i++) {
                xValues[i] = Double.parseDouble(tableModel.getValueAt(i, 0).toString());
                yValues[i] = Double.parseDouble(tableModel.getValueAt(i, 1).toString());
            }
            tabulatedFunction = factory.create(xValues, yValues);
            JOptionPane.showMessageDialog(this, "Функция успешно создана!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose(); // Закрываем окно после успешного создания функции
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Введите корректные значения для всех точек!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (ArrayIsNotSortedException e) {
            JOptionPane.showMessageDialog(this, "Введите точки x в порядке возрастания! Точки должны быть отсортированы!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TabulatedFunctionByArraysWindow::new);
    }
}