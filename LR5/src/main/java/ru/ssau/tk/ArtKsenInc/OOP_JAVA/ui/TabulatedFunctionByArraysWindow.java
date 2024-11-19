
package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.exceptions.ArrayIsNotSortedException;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.IntNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.NumericCellEditor;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class TabulatedFunctionByArraysWindow extends JDialog {
    private final JTextField pointCountField;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final int FIELD_COLUMNS = 5;
    private final int WIDTH_WINDOW = 600;
    private final int HEIGHT_WINDOW = 400;
    private final TabulatedFunctionFactory factory;
    protected TabulatedFunction tabulatedFunction;

    public TabulatedFunctionByArraysWindow(JFrame frame, TabulatedFunctionFactory factory) {
        super(frame, "Создание табулированной функции", true);
        this.factory = factory;

        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ConstantColors.DARK_BLUE);

        // Панель ввода количества точек
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setOpaque(true);
        inputPanel.setBackground(ConstantColors.FRENCH_VIOLET);

        RoundedLabel pointCountLabel = new RoundedLabel("Количество точек:", 10);
        pointCountLabel.setFont(ConstantFonts.Times_New_Roman);
        pointCountLabel.setForeground(ConstantColors.CYAN);
        pointCountField = new JTextField(FIELD_COLUMNS);

        ((AbstractDocument) pointCountField.getDocument()).setDocumentFilter(new IntNumericDocumentFilter());
        JButton createTableButton = createStyledButton("Создать таблицу");

        inputPanel.add(pointCountLabel);
        inputPanel.add(pointCountField);
        inputPanel.add(createTableButton);

        // Панель для таблицы
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);
        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, new NumericCellEditor());
        table.setDefaultRenderer(Object.class, new ColorfulTableCellRenderer());
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);

        tablePanel.setBackground(ConstantColors.INDIGO);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Панель кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ConstantColors.FRENCH_VIOLET);
        JButton createFunctionButton = createStyledButton("Создать функцию");
        buttonPanel.add(createFunctionButton);

        // Добавление панелей на главное окно
        add(inputPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Слушатели для кнопок
        createTableButton.addActionListener(e -> createTable());
        createFunctionButton.addActionListener(e

                -> createTabulatedFunction());

        setVisible(true);
    }

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

        tableModel.setRowCount(0);

        for (int i = 0; i < pointCount; i++) {
            tableModel.addRow(new Object[]{"", ""});
        }
    }

    private void createTabulatedFunction() {
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
            JOptionPane.showMessageDialog(this,

                    "Функция успешно создана!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Введите корректные значения для всех точек!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (ArrayIsNotSortedException e) {
            JOptionPane.showMessageDialog(this, "Введите точки x в порядке возрастания!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(ConstantFonts.Open_Sans_Bold);
        button.setBackground(ConstantColors.FRENCH_VIOLET);


        button.setForeground(ConstantColors.CYAN);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public TabulatedFunction getTabulatedFunction() {
        return null;
    }
}
