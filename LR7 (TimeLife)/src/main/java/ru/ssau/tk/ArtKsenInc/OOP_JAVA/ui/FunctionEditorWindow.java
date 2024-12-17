package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.DoubleNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters.IntNumericDocumentFilter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows.SettingsWindowChooseTheWayCreateTF;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.io.FunctionsIO;

public class FunctionEditorWindow extends JDialog {

    private TabulatedFunction function;
    private final TabulatedFunctionOperationService factoryService;
    private XYSeriesCollection dataset = new XYSeriesCollection(); // Данные для графика
    private ChartPanel chartPanel;
    private final JFrame owner;

    public FunctionEditorWindow(JFrame frame, TabulatedFunctionOperationService factoryService) {
        super(frame, "Редактор табулированной функции", true);
        owner = frame;
        this.factoryService = factoryService;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Установка фона и шрифта для всего окна
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Панель с кнопками
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 3)); // 2 ряда, 3 колонки
        buttonsPanel.setBackground(ConstantColors.DARK_PURPLE);

        // Кнопка для создания функции
        JButton createButton = createStyledButton("Создать функцию");
        createButton.addActionListener(this::createFunction);
        buttonsPanel.add(createButton);

        // Кнопка для загрузки функции
        JButton loadButton = createStyledButton("Загрузить функцию");
        loadButton.addActionListener(this::loadFunction);
        buttonsPanel.add(loadButton);

        // Кнопка для сохранения функции
        JButton saveButton = createStyledButton("Сохранить функцию");
        saveButton.addActionListener(this::saveFunction);
        buttonsPanel.add(saveButton);

        // Кнопка для вычисления значения в произвольной точке
        JButton calculateButton = createStyledButton("Вычислить значение в точке");
        calculateButton.addActionListener(this::calculateValueAtPoint);
        buttonsPanel.add(calculateButton);

        JButton insertButton = createStyledButton("Вставка");
        insertButton.addActionListener(e -> InsertValueInTB());
        buttonsPanel.add(insertButton);

        JButton removeButton = createStyledButton("Удалить");
        removeButton.addActionListener(e -> DeleteValueInTB());
        buttonsPanel.add(removeButton);

        add(buttonsPanel, BorderLayout.NORTH);

        // Создание графика
        JFreeChart chart = ChartFactory.createXYLineChart(
                "График табулированной функции",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(ConstantColors.RICH_PURPLE);
        chart.getTitle().setPaint(ConstantColors.BLACK);
        chart.getXYPlot().setBackgroundPaint(ConstantColors.DARK_PURPLE);
        add(chartPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Метод для создания функции
    private void createFunction(ActionEvent event) {
        SettingsWindowChooseTheWayCreateTF createWindow = new SettingsWindowChooseTheWayCreateTF(owner, factoryService);
        createWindow.setVisible(true);
        function = createWindow.getTabulatedFunction();  // Получаем функцию
        if (function != null) {
            updateChart();  // Обновляем график
        }
    }

    private void loadFunction(ActionEvent actionEvent) {
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
                        this.function = FunctionsIO.readTabulatedFunction(reader, factoryService.getFactory());
                    }
                } else if (fileName.endsWith(".bin")) {
                    this.function = FunctionsIO.deserialize(bufferedInputStream);
                } else {
                    throw new IOException("Неподдерживаемый формат файла");
                }
                updateChart();
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Ошибка загрузки функции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFunction(ActionEvent actionEvent) {
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
                TabulatedFunction function = this.function;
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

    // Метод для вычисления значения в произвольной точке
    private void calculateValueAtPoint(ActionEvent event) {
        if (function == null) {
            JOptionPane.showMessageDialog(this, "Функция не создана", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Создаем текстовое поле
        JTextField textField = new JTextField(10);

        // Применяем фильтр к документу текстового поля
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DoubleNumericDocumentFilter());

        // Создаем панель для размещения текстового поля
        JPanel panel = new JPanel();
        panel.add(new JLabel("Введите значение X:"));
        panel.add(textField);

        // Показываем диалоговое окно
        int res = JOptionPane.showConfirmDialog(null, panel, "Введите значение X:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String input = textField.getText();
        if (res == JOptionPane.OK_OPTION) {
            try {
                double x = Double.parseDouble(input);
                double result = function.apply(x);
                JOptionPane.showMessageDialog(this, "Значение функции в точке " + x + " равно " + result);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Некорректный ввод", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
                updateChart();
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
                updateChart();
                JOptionPane.showMessageDialog(this, "Точка удалена");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Строка не существует", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Метод для обновления графика
    private void updateChart() {
        if (function == null) {
            return;
        }
        XYSeries series = new XYSeries("Табулированная функция");
        for (int i = 0; i < function.getCount(); i++) {
            series.add(function.getX(i), function.getY(i));
        }
        dataset.removeAllSeries();
        dataset.addSeries(series);
    }

    // Метод для создания стилизованной кнопки
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(ConstantFonts.Open_Sans_Bold);
        button.setBackground(ConstantColors.DARK_PURPLE);
        button.setForeground(ConstantColors.THISTLE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Pointer при наведении
        return button;
    }
}
