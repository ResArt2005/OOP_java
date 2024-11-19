package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedDifferentialOperator;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.DoubleNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.IntNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.NumericCellEditor;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows.SettingsWindowChooseTheWayCreateTF;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.io.FunctionsIO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
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
                _ -> createFunction(), _ -> loadFunction(), _ -> saveFunction(1),  _ -> DeleteValueInTB(), _ -> InsertValueInTB());
        JPanel resultFunctionPanel = createResultPanel();
        // Панель с операциями
        JPanel operationPanel = new JPanel();
        operationPanel.setLayout(new BorderLayout());
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
    private JPanel createFunctionPanel(String title, JTable table, ActionListener createListener, ActionListener loadListener, ActionListener saveListener, ActionListener deleteListener, ActionListener insertListener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(null, title, 0, 0, ConstantFonts.Open_Sans_Bold, ConstantColors.CYAN));
        panel.setBackground(ConstantColors.INDIGO); // Фон панели

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Новый JPanel для кнопок
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(ConstantColors.INDIGO); // Фон панели кнопок
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Добавляем отступы между кнопками

        // Кнопка Создать
        gbc.gridx = 0; // первая колонка
        gbc.gridy = 0; // первая строка
        buttonPanel.add(createStyledButton("Создать", createListener), gbc);

        // Кнопка Загрузить
        gbc.gridx = 1; // вторая колонка
        buttonPanel.add(createStyledButton("Загрузить", loadListener), gbc);

        // Кнопка Удалить
        gbc.gridx = 0; // первая колонка
        gbc.gridy = 1; // вторая строка
        buttonPanel.add(createStyledButton("Удалить", deleteListener), gbc);

        // Кнопка Вставка
        gbc.gridx = 1; // вторая колонка
        buttonPanel.add(createStyledButton("Вставка", insertListener), gbc);

        // Кнопка Сохранить
        gbc.gridx = 0; // возвращаемся в первую колонку
        gbc.gridy = 2; // теперь на третью строку
        gbc.gridwidth = 2; // этот компонент занимает две колонки
        gbc.anchor = GridBagConstraints.CENTER; // центруем по горизонтали
        buttonPanel.add(createStyledButton("Сохранить", saveListener), gbc);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
        private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(ConstantFonts.Open_Sans_Bold);
        button.setBackground(ConstantColors.FRENCH_VIOLET);
        button.setForeground(ConstantColors.CYAN);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Pointer при наведении
        return button;
    }
    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = createStyledButton(text);
        button.addActionListener(listener);
        return button;
    }

    // Метод для создания панели результата
    private JPanel createResultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Результат"));

        JScrollPane scrollPane = new JScrollPane(resultFunctionTable);
        resultFunctionTable.setPreferredScrollableViewportSize(new Dimension(400, 200)); // Задайте нужный размер
        panel.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(_ -> saveFunction(2));

        panel.add(saveButton, new GridBagConstraints(0, 1, 1, 0, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));

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

    private void InsertValueInTB() {
        if (function == null) {
            JOptionPane.showMessageDialog(this, "Функция не создана", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Создаем текстовое поле
        JTextField XField = new JTextField(10);
        JTextField YField = new JTextField(10);

        // Применяем фильтр к документу текстового поля
        ((AbstractDocument) XField.getDocument()).setDocumentFilter(new DoubleNumericDocumentFilter());
        ((AbstractDocument) YField.getDocument()).setDocumentFilter(new DoubleNumericDocumentFilter());

        // Создаем панель для размещения текстового поля
        JPanel panel = new JPanel();
        panel.add(new JLabel("Введите значение X:"));
        panel.add(XField);
        panel.add(new JLabel("Введите значение Y:"));
        panel.add(YField);
        // Показываем диалоговое окно
        int res = JOptionPane.showConfirmDialog(null, panel, "Введите значение функции X и Y:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        String X = XField.getText();
        String Y = YField.getText();
        if (res == JOptionPane.OK_OPTION) {
            try {
                double x = Double.parseDouble(X);
                double y = Double.parseDouble(Y);
                function.insert(x, y);
                updateTableWithFunction(firstTableModel, function);
                JOptionPane.showMessageDialog(this, "Добавлена точка (" + x + ", " + y + ")");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Некорректный ввод", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void DeleteValueInTB() {

        if (function == null) {
            JOptionPane.showMessageDialog(this, "Функция не создана", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Создаем текстовое поле
        JTextField indexField = new JTextField(10);

        // Применяем фильтр к документу текстового поля
        ((AbstractDocument) indexField.getDocument()).setDocumentFilter(new IntNumericDocumentFilter());

        // Создаем панель для размещения текстового поля
        JPanel panel = new JPanel();
        panel.add(new JLabel("Введите номер строки (индексация с 1 до n строки) для удаления:"));
        panel.add(indexField);
        // Показываем диалоговое окно
        int res = JOptionPane.showConfirmDialog(null, panel, "Удаление точки", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        String index = indexField.getText();
        if (res == JOptionPane.OK_OPTION) {
            try {
                int i = Integer.parseInt(index);
                function.remove(i - 1);
                updateTableWithFunction(firstTableModel, function);
                JOptionPane.showMessageDialog(this, "Точка удалена");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Строка не существует", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
