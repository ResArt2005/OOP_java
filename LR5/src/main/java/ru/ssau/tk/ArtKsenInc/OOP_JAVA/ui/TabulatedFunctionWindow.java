package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.LinkedListTabulatedFunctionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabulatedFunctionWindow extends JFrame {
    private JTextField pointCountField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel tablePanel;
    final int FIELD_COLUMNS = 5;  // Количество видимых символов
    final int WIDTH_WINDOW = 600; //Ширина окна
    final int HEIGHT_WINDOW = 400; //Высота окна
    private LinkedListTabulatedFunctionFactory factory;
    private TabulatedFunction tabulatedFunction;

    public TabulatedFunctionWindow() {
        factory = new LinkedListTabulatedFunctionFactory();
        setTitle("Создание табулированной функции");
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель ввода количества точек
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel pointCountLabel = new JLabel("Количество точек:");
        pointCountField = new JTextField(FIELD_COLUMNS);
        JButton createTableButton = new JButton("Создать таблицу");

        inputPanel.add(pointCountLabel);
        inputPanel.add(pointCountField);
        inputPanel.add(createTableButton);

        // Панель для таблицы
        tablePanel = new JPanel();
        tableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Кнопка для создания табулированной функции
        JButton createFunctionButton = new JButton("Создать функцию");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createFunctionButton);

        // Добавление компонентов на главное окно
        add(inputPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Действие на кнопку создания таблицы
        createTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable();
            }
        });

        // Действие на кнопку создания функции
        createFunctionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTabulatedFunction();
            }
        });
    }

    // Метод для создания таблицы на основе количества точек
    private void createTable() {
        int pointCount;
        try {
            pointCount = Integer.parseInt(pointCountField.getText());
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
            dispose(); // Закрываем окно после успешного создания функции
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Введите корректные значения для всех точек!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TabulatedFunctionWindow window = new TabulatedFunctionWindow();
            window.setVisible(true);
        });
    }
}