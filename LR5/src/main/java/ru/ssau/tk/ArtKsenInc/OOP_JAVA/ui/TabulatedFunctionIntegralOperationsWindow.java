package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent.TabulatedIntegrationExecutor;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.DoubleNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.IntNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.NumericCellEditor;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ColorfulTableCellRenderer;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows.SettingsWindowChooseTheWayCreateTF;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.io.FunctionsIO;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class TabulatedFunctionIntegralOperationsWindow extends JDialog {
    private TabulatedFunctionOperationService operationService;
    private TabulatedIntegrationExecutor integrationExecutor;
    private TabulatedFunction function;
    private double integralResult;

    private final JTable functionTable;
    private final JTextField threadsCountField;
    private final DefaultTableModel firstTableModel;

    SettingsWindowChooseTheWayCreateTF settingsWindowChooseTheWayCreateTF;

    JFrame owner;

    public TabulatedFunctionIntegralOperationsWindow(JFrame frame, TabulatedFunctionOperationService operationService) {
        super(frame, "Вычисление интеграла табулированной функции", true);
        owner = frame;
        this.operationService = operationService;
        this.integrationExecutor = new TabulatedIntegrationExecutor(1); // По умолчанию 1 поток
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Установка фона и шрифта для всего окна
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Таблицы для функций
        firstTableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);

        functionTable = createTable(firstTableModel, true);

        // Панели для таблиц и кнопок
        JPanel firstFunctionPanel = createFunctionPanel("Функция", functionTable,
                _ -> createFunction(), _ -> loadFunction(), _ -> saveFunction(), _ -> DeleteValueInTB(firstTableModel, function), _ -> InsertValueInTB(firstTableModel, function));

        // Панель с операциями
        JPanel operationPanel = new JPanel();
        operationPanel.setLayout(new GridLayout(2, 1));
        operationPanel.setBackground(ConstantColors.DARK_PURPLE); // Цвет фона

        JButton integrateButton = createStyledButton("Вычислить интеграл");

        // Добавляем заголовок и текстовое поле для количества потоков
        JLabel threadsCountLabel = new JLabel("Количество потоков:");
        threadsCountLabel.setFont(ConstantFonts.Open_Sans_Bold);
        threadsCountLabel.setForeground(ConstantColors.TIFFANY_BLUE);
        threadsCountField = new JTextField(10);
        threadsCountField.setFont(ConstantFonts.Open_Sans_Bold);
        threadsCountField.setBackground(ConstantColors.FRENCH_VIOLET);
        threadsCountField.setForeground(ConstantColors.TIFFANY_BLUE);
        ((AbstractDocument) threadsCountField.getDocument()).setDocumentFilter(new IntNumericDocumentFilter());

        JPanel threadsCountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        threadsCountPanel.setBackground(ConstantColors.DARK_PURPLE);
        threadsCountPanel.add(threadsCountLabel);
        threadsCountPanel.add(threadsCountField);

        integrateButton.addActionListener(_ -> performIntegration());

        operationPanel.add(threadsCountPanel);
        operationPanel.add(integrateButton);

        // Размещение элементов в окне
        JPanel functionsPanel = new JPanel(new GridLayout(1, 1));
        functionsPanel.setBackground(ConstantColors.DARK_PURPLE);
        functionsPanel.add(firstFunctionPanel);

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

    // Метод для создания таблицы с возможностью редактирования
    private JTable createTable(DefaultTableModel tableModel, boolean editable) {
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return editable && column != 0; // Колонка x не редактируется
            }
        };
        // Добавляем слушатель изменений модели таблицы
        tableModel.addTableModelListener(e -> {
            if (function != null && e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                if (column == 1) { // Обновляем только значения Y
                    try {
                        double newValue = Double.parseDouble(tableModel.getValueAt(row, column).toString());
                        function.setY(row, newValue); // Синхронизация с функцией
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(table, "Введите корректное числовое значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        // Установка кастомного рендера для ячеек таблицы
        table.getColumnModel().getColumn(0).setCellRenderer(
                new ColorfulTableCellRenderer(ConstantColors.FRENCH_VIOLET, ConstantColors.DARK_BLUE, ConstantColors.TIFFANY_BLUE, "Open Sans"));
        table.getColumnModel().getColumn(1).setCellRenderer(
                new ColorfulTableCellRenderer(ConstantColors.FRENCH_VIOLET, ConstantColors.DARK_BLUE, ConstantColors.TIFFANY_BLUE, "Open Sans"));

        table.setRowHeight(25);  // Высота строки

        // Установка кастомного заголовка для таблицы
        JTableHeader header = table.getTableHeader();
        header.setBackground(ConstantColors.DARK_BLUE);  // Тёмно-синий фон заголовков
        header.setForeground(ConstantColors.TIFFANY_BLUE);       // Циановый цвет текста заголовков
        header.setFont(new Font("Open Sans", Font.BOLD, 15));  // Шрифт заголовков
        table.setDefaultEditor(Object.class, new NumericCellEditor());
        return table;
    }

    // Метод для выполнения операций над функциями
    private void performIntegration() {
        if (function == null) {
            JOptionPane.showMessageDialog(this, "Функция должна быть создана или загружена", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int threadsCount = Integer.parseInt(threadsCountField.getText());
            if (threadsCount <= 0) {
                throw new IllegalArgumentException("Количество потоков должно быть больше 0");
            }
            integrationExecutor.shutdown(); // Закрываем предыдущий ExecutorService
            integrationExecutor = new TabulatedIntegrationExecutor(threadsCount);
            integralResult = integrationExecutor.Integrate(function);
            JOptionPane.showMessageDialog(this, "Результат интеграла: " + integralResult, "Результат", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Некорректный ввод числа потоков", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
            //JOptionPane.showMessageDialog(this, "Функция не была создана", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFunction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Все поддерживаемые файлы", "json", "xml", "bin"));
        fileChooser.setAcceptAllFileFilterUsed(true);

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fileName = file.getName().toLowerCase();
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
                if (fileName.endsWith(".json") || fileName.endsWith(".xml")) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedInputStream))) {
                        this.function = FunctionsIO.readTabulatedFunction(reader, operationService.getFactory());
                    }
                } else if (fileName.endsWith(".bin")) {
                    this.function = FunctionsIO.deserialize(bufferedInputStream);
                } else {
                    throw new IOException("Неподдерживаемый формат файла");
                }
                updateTableWithFunction(firstTableModel, function);
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Ошибка загрузки функции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFunction() {
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
                if (file.getName().endsWith(".json") || file.getName().endsWith(".xml")) {
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(bufferedOutputStream))) {
                        FunctionsIO.writeTabulatedFunction(writer, this.function);
                    }
                } else if (file.getName().endsWith(".bin")) {
                    FunctionsIO.serialize(bufferedOutputStream, this.function);
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
            //JOptionPane.showMessageDialog(this, "Функция не создана", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
