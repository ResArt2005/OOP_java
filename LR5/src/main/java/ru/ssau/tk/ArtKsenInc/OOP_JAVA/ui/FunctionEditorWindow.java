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

        // Панель с кнопками
        JPanel buttonsPanel = new JPanel(new FlowLayout());

        // Кнопка для создания функции
        JButton createButton = new JButton("Создать функцию");
        createButton.addActionListener(this::createFunction);
        buttonsPanel.add(createButton);

        // Кнопка для загрузки функции
        JButton loadButton = new JButton("Загрузить функцию");
        loadButton.addActionListener(this::loadFunction);
        buttonsPanel.add(loadButton);

        // Кнопка для сохранения функции
        JButton saveButton = new JButton("Сохранить функцию");
        saveButton.addActionListener(this::saveFunction);
        buttonsPanel.add(saveButton);

        // Кнопка для вычисления значения в произвольной точке
        JButton calculateButton = new JButton("Вычислить значение в точке");
        calculateButton.addActionListener(this::calculateValueAtPoint);
        buttonsPanel.add(calculateButton);

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

    // Метод для загрузки функции из файла
    private void loadFunction(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
                function = FunctionsIO.deserialize(bufferedInputStream);
                updateChart();  // Обновляем график после загрузки функции
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Ошибка загрузки функции", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Метод для сохранения функции в файл
    private void saveFunction(ActionEvent event) {
        if (function == null) {
            JOptionPane.showMessageDialog(this, "Функция не создана", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
                TabulatedFunction function = this.function;
                FunctionsIO.serialize(bufferedOutputStream, function);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Ошибка сохранения функции", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
        int res =  JOptionPane.showConfirmDialog(null, panel, "Введите значение X:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
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

}
