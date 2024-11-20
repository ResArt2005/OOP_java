package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.DoubleNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.IntNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.NumericCellEditor;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ColorfulTableCellRenderer;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows.SettingsWindowChooseTheWayCreateTF;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.io.FunctionsIO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class TabulatedFunctionOperationsWindow extends JDialog {
    private final TabulatedFunctionOperationService operationService;
    private TabulatedFunction firstFunction;
    private TabulatedFunction secondFunction;
    private TabulatedFunction resultFunction;

    private final JTable firstFunctionTable;
    private final JTable secondFunctionTable;
    private final JTable resultFunctionTable;

    private final DefaultTableModel firstTableModel;
    private final DefaultTableModel secondTableModel;
    private final DefaultTableModel resultTableModel;

    SettingsWindowChooseTheWayCreateTF settingsWindowChooseTheWayCreateTF;

    private final int operand_1 = 1;
    private final int operand_2 = 2;
    private final int result = 3;
    private final int SUM = 1;
    private final int SUBTRACT = 2;
    private final int MULTIPLY = 3;
    private final int DIVIDE = 4;
    JFrame owner;

    public TabulatedFunctionOperationsWindow(JFrame frame, TabulatedFunctionOperationService operationService) {
        super(frame, "Операции с табулированными функциями", true);
        owner = frame;
        this.operationService = operationService;
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Установка фона и шрифта для всего окна
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Таблицы для функций
        firstTableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);
        secondTableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);
        resultTableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);

        firstFunctionTable = createTable(firstTableModel, true, operand_1);
        secondFunctionTable = createTable(secondTableModel, true, operand_2);
        resultFunctionTable = createTable(resultTableModel, false, -1);

        // Панели для таблиц и кнопок
        JPanel firstFunctionPanel = createFunctionPanel("Первая функция", firstFunctionTable,
                _ -> createFunction(operand_1), _ -> loadFunction(operand_1), _ -> saveFunction(operand_1), _ -> DeleteValueInTB(firstTableModel, firstFunction), _ -> InsertValueInTB(firstTableModel, firstFunction));
        JPanel secondFunctionPanel = createFunctionPanel("Вторая функция", secondFunctionTable,
                _ -> createFunction(operand_2), _ -> loadFunction(operand_2), _ -> saveFunction(operand_2), _ -> DeleteValueInTB(secondTableModel, secondFunction), _ -> InsertValueInTB(secondTableModel, secondFunction));
        JPanel resultFunctionPanel = createResultPanel();

        // Панель с операциями
        JPanel operationPanel = new JPanel();
        operationPanel.setLayout(new GridLayout(1, 4));
        operationPanel.setBackground(ConstantColors.DARK_PURPLE); // Цвет фона

        JButton sumButton = createStyledButton("Сложение");
        JButton subtractButton = createStyledButton("Вычитание");
        JButton multiplyButton = createStyledButton("Умножение");
        JButton divideButton = createStyledButton("Деление");

        sumButton.addActionListener(_ -> performOperation(SUM));
        subtractButton.addActionListener(_ -> performOperation(SUBTRACT));
        multiplyButton.addActionListener(_ -> performOperation(MULTIPLY));
        divideButton.addActionListener(_ -> performOperation(DIVIDE));

        operationPanel.add(sumButton);
        operationPanel.add(subtractButton);
        operationPanel.add(multiplyButton);
        operationPanel.add(divideButton);

        // Размещение элементов в окне
        JPanel functionsPanel = new JPanel(new GridLayout(1, 3));
        functionsPanel.setBackground(ConstantColors.DARK_PURPLE);
        functionsPanel.add(firstFunctionPanel);
        functionsPanel.add(secondFunctionPanel);
        functionsPanel.add(resultFunctionPanel);

        add(functionsPanel, BorderLayout.CENTER);
        add(operationPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    // Метод для создания панели с функциями (создать, загрузить, сохранить)
    private JPanel createFunctionPanel(String title, JTable table, ActionListener createListener, ActionListener loadListener, ActionListener saveListener, ActionListener deleteListener, ActionListener insertListener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(null, title, 0, 0, ConstantFonts.Open_Sans_Bold, ConstantColors.TIFFANY_BLUE));
        panel.setBackground(ConstantColors.DARK_PURPLE); // Фон панели

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Новый JPanel для кнопок
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(ConstantColors.DARK_PURPLE); // Фон панели кнопок
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


    // Метод для создания панели результата
    private JPanel createResultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(null, "Результат", 0, 0, ConstantFonts.Open_Sans_Bold, ConstantColors.TIFFANY_BLUE));
        panel.setBackground(ConstantColors.DARK_PURPLE); // Фон панели

        JScrollPane scrollPane = new JScrollPane(resultFunctionTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton saveButton = createStyledButton("Сохранить", _ -> saveFunction(result));
        panel.add(saveButton, BorderLayout.SOUTH);

        return panel;
    }

    private JTable createTable(DefaultTableModel tableModel, boolean editable, int operand) {
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return editable && column != 0; // Колонка x не редактируется
            }
        };
        // Добавляем слушатель изменений модели таблицы
        tableModel.addTableModelListener(e -> {
            if (operand == operand_1) {
                if (firstFunction != null && e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    if (column == 1) { // Обновляем только значения Y
                        try {
                            double newValue = Double.parseDouble(tableModel.getValueAt(row, column).toString());
                            firstFunction.setY(row, newValue); // Синхронизация с функцией
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(table, "Введите корректное числовое значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else if (operand == operand_2) {
                if (secondFunction != null && e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    if (column == 1) { // Обновляем только значения Y
                        try {
                            double newValue = Double.parseDouble(tableModel.getValueAt(row, column).toString());
                            secondFunction.setY(row, newValue); // Синхронизация с функцией
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(table, "Введите корректное числовое значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        // Установка кастомного рендера для ячеек таблицы
        table.getColumnModel().getColumn(0).setCellRenderer(
                new ColorfulTableCellRenderer(ConstantColors.FRENCH_VIOLET, ConstantColors.FRENCH_VIOLET, ConstantColors.TIFFANY_BLUE, "Open Sans"));
        table.getColumnModel().getColumn(1).setCellRenderer(
                new ColorfulTableCellRenderer(ConstantColors.FRENCH_VIOLET, ConstantColors.FRENCH_VIOLET, ConstantColors.TIFFANY_BLUE, "Open Sans"));

        table.setRowHeight(25);  // Высота строки

        // Установка кастомного заголовка для таблицы
        JTableHeader header = table.getTableHeader();
        header.setBackground(ConstantColors.DARK_BLUE);  // Тёмно-синий фон заголовков
        header.setForeground(ConstantColors.TIFFANY_BLUE);       // Циановый цвет текста заголовков
        header.setFont(new Font("Open Sans", Font.BOLD, 15));  // Шрифт заголовков
        table.setDefaultEditor(Object.class, new NumericCellEditor());
        return table;
    }


    // Метод для создания стилизованной кнопки
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(ConstantFonts.Open_Sans_Bold);
        button.setBackground(ConstantColors.FRENCH_VIOLET);
        button.setForeground(ConstantColors.TIFFANY_BLUE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Pointer при наведении
        return button;
    }

    // Перегруженный метод для создания кнопок с обработчиками событий
    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = createStyledButton(text);
        button.addActionListener(listener);
        return button;
    }

    // Метод для выполнения операций над функциями
    private void performOperation(int operation) {
        if (firstFunction == null || secondFunction == null) {
            JOptionPane.showMessageDialog(this, "Обе функции должны быть созданы или загружены", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            switch (operation) {
                case SUM:
                    resultFunction = operationService.sum(firstFunction, secondFunction);
                    break;
                case SUBTRACT:
                    resultFunction = operationService.subtract(firstFunction, secondFunction);
                    break;
                case MULTIPLY:
                    resultFunction = operationService.multiplication(firstFunction, secondFunction);
                    break;
                case DIVIDE:
                    resultFunction = operationService.division(firstFunction, secondFunction);
                    break;
            }
            updateTableWithFunction(resultTableModel, resultFunction);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при выполнении операции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Методы для создания, загрузки и сохранения функций
    private void createFunction(int operand) {
        if (settingsWindowChooseTheWayCreateTF == null || !settingsWindowChooseTheWayCreateTF.isShowing()) {
            settingsWindowChooseTheWayCreateTF = new SettingsWindowChooseTheWayCreateTF(owner, operationService);
            settingsWindowChooseTheWayCreateTF.setVisible(true); // Показываем модальное окно
        }
        TabulatedFunction createdFunction = settingsWindowChooseTheWayCreateTF.getTabulatedFunction();
        // Проверяем, какая функция была выбрана (первая или вторая)
        if (createdFunction != null) {
            if (operand == operand_1) {
                firstFunction = createdFunction;
                updateTableWithFunction(firstTableModel, firstFunction);
            } else if (operand == operand_2) {
                secondFunction = createdFunction;
                updateTableWithFunction(secondTableModel, secondFunction);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Функция не была создана", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFunction(int operand) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Все поддерживаемые файлы", "json", "xml", "bin"));
        fileChooser.setAcceptAllFileFilterUsed(true);

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fileName = file.getName().toLowerCase();
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
                TabulatedFunction function;
                if (fileName.endsWith(".json") || fileName.endsWith(".xml")) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedInputStream))) {
                        function = FunctionsIO.readTabulatedFunction(reader, operationService.getFactory());
                    }
                } else if (fileName.endsWith(".bin")) {
                    function = FunctionsIO.deserialize(bufferedInputStream);
                } else {
                    throw new IOException("Неподдерживаемый формат файла");
                }
                if (operand == operand_1) {
                    firstFunction = function;
                    updateTableWithFunction(firstTableModel, firstFunction);
                } else if (operand == operand_2) {
                    secondFunction = function;
                    updateTableWithFunction(secondTableModel, secondFunction);
                }
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Ошибка загрузки функции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void saveFunction(int operand) {
        JFileChooser fileChooser = new JFileChooser();

        // Добавляем фильтры файлов
        javax.swing.filechooser.FileNameExtensionFilter allFormatsFilter =
                new javax.swing.filechooser.FileNameExtensionFilter("Все поддерживаемые файлы", "json", "xml", "bin");
        fileChooser.setFileFilter(allFormatsFilter); // Устанавливаем его как начальный
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON файлы", "json"));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XML файлы", "xml"));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Бинарные файлы", "bin"));

        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fileName = file.getName().toLowerCase();

            // Определяем выбранный фильтр
            String selectedExtension = "";
            javax.swing.filechooser.FileFilter selectedFilter = fileChooser.getFileFilter();
            if (selectedFilter.getDescription().contains("JSON")) {
                selectedExtension = ".json";
            } else if (selectedFilter.getDescription().contains("XML")) {
                selectedExtension = ".xml";
            } else if (selectedFilter.getDescription().contains("Бинарные")) {
                selectedExtension = ".bin";
            }

            // Автоматически добавляем расширение, если его нет
            if (!fileName.endsWith(".json") && !fileName.endsWith(".xml") && !fileName.endsWith(".bin")) {
                file = new File(file.getAbsolutePath() + selectedExtension);
            }

            try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))) {
                TabulatedFunction function = (operand == operand_1) ? firstFunction : (operand == operand_2) ? secondFunction : resultFunction;
                if (file.getName().endsWith(".json") || file.getName().endsWith(".xml")) {
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(bufferedOutputStream))) {
                        FunctionsIO.writeTabulatedFunction(writer, function);
                    }
                } else if (file.getName().endsWith(".bin")) {
                    FunctionsIO.serialize(bufferedOutputStream, function);
                } else {
                    throw new IOException("Неподдерживаемый формат файла");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Ошибка сохранения функции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void InsertValueInTB(DefaultTableModel tableModel, TabulatedFunction function) {
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
                updateTableWithFunction(tableModel, function);
                JOptionPane.showMessageDialog(this, "Добавлена точка (" + x + ", " + y + ")");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Некорректный ввод", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void DeleteValueInTB(DefaultTableModel tableModel, TabulatedFunction function) {

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
                updateTableWithFunction(tableModel, function);
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
