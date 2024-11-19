package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedDifferentialOperator;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.NumericCellEditor;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows.SettingsWindowChooseTheWayCreateTF;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.io.FunctionsIO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class TabulatedFunctionDifferentialOperationsWindow extends JDialog {
    private final TabulatedDifferentialOperator operationService;
    private TabulatedFunction function;
    private TabulatedFunction resultFunction;

    private final JTable functionTable;
    private final JTable resultFunctionTable;

    private final DefaultTableModel firstTableModel;
    private final DefaultTableModel resultTableModel;

    SettingsWindowChooseTheWayCreateTF settingsWindowChooseTheWayCreateTF;

    JFrame owner = new JFrame();

    public TabulatedFunctionDifferentialOperationsWindow(JFrame frame, TabulatedDifferentialOperator operationService) {
        super(frame, "Операции с табулированными функциями", true);
        owner = frame;
        this.operationService = operationService;
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Таблицы для функций
        firstTableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);
        resultTableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);

        functionTable = createTable(firstTableModel, true);
        resultFunctionTable = createTable(resultTableModel, false);

        // Панели для таблиц и кнопок
        JPanel firstFunctionPanel = createFunctionPanel("Функция", functionTable,
                _ -> createFunction(), _ -> loadFunction(), _ -> saveFunction(1));
        JPanel resultFunctionPanel = createResultPanel();
        // Панель с операциями
        JPanel operationPanel = new JPanel();
        operationPanel.setLayout(new GridLayout(4, 1));
        JButton sumButton = new JButton("Дифференцировать");

        sumButton.addActionListener(_ -> performOperation());

        operationPanel.add(sumButton);

        // Размещение элементов в окне
        JPanel functionsPanel = new JPanel(new GridLayout(1, 3));
        functionsPanel.add(firstFunctionPanel);
        functionsPanel.add(resultFunctionPanel);

        add(functionsPanel, BorderLayout.CENTER);
        add(operationPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    // Метод для создания панели с функциями (создать, загрузить, сохранить)
    private JPanel createFunctionPanel(String title, JTable table, ActionListener createListener, ActionListener loadListener, ActionListener saveListener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton createButton = new JButton("Создать");
        JButton loadButton = new JButton("Загрузить");
        JButton saveButton = new JButton("Сохранить");

        createButton.addActionListener(createListener);
        loadButton.addActionListener(loadListener);
        saveButton.addActionListener(saveListener);

        buttonPanel.add(createButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Метод для создания панели результата
    private JPanel createResultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Результат"));

        JScrollPane scrollPane = new JScrollPane(resultFunctionTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(_ -> saveFunction(2));
        panel.add(saveButton, BorderLayout.SOUTH);

        return panel;
    }

    // Метод для создания таблицы с возможностью редактирования
    private JTable createTable(DefaultTableModel tableModel, boolean editable) {
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return editable && column != 0; // Колонка x не редактируется
            }
        };
        table.setDefaultEditor(Object.class, new NumericCellEditor());
        return table;
    }

    // Метод для выполнения операций над функциями
    private void performOperation() {
        if (function == null) {
            JOptionPane.showMessageDialog(this, "Функция должна быть создана или загружена", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            resultFunction = operationService.derive(function);
            updateTableWithFunction(resultTableModel, resultFunction);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при выполнении операции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Методы для создания, загрузки и сохранения функций
    private void createFunction() {
        if (settingsWindowChooseTheWayCreateTF == null || !settingsWindowChooseTheWayCreateTF.isShowing()) {
            settingsWindowChooseTheWayCreateTF = new SettingsWindowChooseTheWayCreateTF(owner, new TabulatedFunctionOperationService(operationService.getFactory()));
            settingsWindowChooseTheWayCreateTF.setVisible(true); // Показываем модальное окно
        }
        TabulatedFunction createdFunction = settingsWindowChooseTheWayCreateTF.getTabulatedFunction();
        // Проверяем, какая функция была выбрана (первая или вторая)
        if (createdFunction != null) {
            function = createdFunction;
            updateTableWithFunction(firstTableModel, function);
        } else {
            JOptionPane.showMessageDialog(this, "Функция не была создана", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFunction() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
                this.function = FunctionsIO.deserialize(bufferedInputStream);
                updateTableWithFunction(firstTableModel, function);
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Ошибка загрузки функции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFunction(int operand) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
                TabulatedFunction function = (operand == 1) ? this.function : resultFunction;
                FunctionsIO.serialize(bufferedOutputStream, function);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Ошибка сохранения функции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Обновление таблицы значениями из табулированной функции
    private void updateTableWithFunction(DefaultTableModel tableModel, TabulatedFunction function) {
        tableModel.setRowCount(0); // Очищаем таблицу
        for (int i = 0; i < function.getCount(); i++) {
            tableModel.addRow(new Object[]{function.getX(i), function.getY(i)});
        }
    }
}
