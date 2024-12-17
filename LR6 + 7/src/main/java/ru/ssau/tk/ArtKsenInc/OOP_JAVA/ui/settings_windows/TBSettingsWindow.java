package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.TBFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.RoundedLabel;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class TBSettingsWindow extends JDialog {
    private final int WIDTH_WINDOW = 600;
    private final int HEIGHT_WINDOW = 400;
    private final JPanel TBPanel = new JPanel(); // Панель для отображения логов
    private JFrame owner;
    TabulatedFunctionOperationService factoryService;

    public TBSettingsWindow(JFrame frame, TabulatedFunctionOperationService factoryService) {
        super(frame, "Табулированные функции", true);
        owner = frame;
        this.factoryService = factoryService;
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Создание и добавление заголовка с закругленными краями
        RoundedLabel headerLabel = new RoundedLabel("Табулированные функции", 20, ConstantColors.DEEP_BLUE);
        headerLabel.setBackground(ConstantColors.DARK_LILAC);
        headerLabel.setForeground(ConstantColors.DEEP_BLUE);
        headerLabel.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(24f)); // Увеличенный шрифт для заголовка
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(WIDTH_WINDOW, 50));
        headerLabel.setBorder(BorderFactory.createLineBorder(ConstantColors.DARK_VIOLET, 2)); // Добавляем темно-бордовую обводку рамки
        add(headerLabel, BorderLayout.NORTH);

        TBPanel.setLayout(new BorderLayout());
        TBPanel.setBackground(ConstantColors.BLACK);
        TBPanel.setForeground(ConstantColors.DARK_GREEN);
        updatePanel(TBPanel);
        add(TBPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(ConstantColors.DARK_PURPLE);

        JButton createButton = new JButton("Добавить");
        createButton.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(16f));
        createButton.setBackground(ConstantColors.RICH_PURPLE);
        createButton.setForeground(ConstantColors.THISTLE);;
        createButton.setFocusPainted(false);
        createButton.addActionListener(e -> {
            createTBFunc(TBPanel); // Очищаем логи при нажатии
        });
        JButton eraseButton = new JButton("Стереть все");
        eraseButton.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(16f));
        eraseButton.setBackground(ConstantColors.RICH_PURPLE);
        eraseButton.setForeground(ConstantColors.THISTLE);;
        eraseButton.setFocusPainted(false);

        // Добавляем слушатель для кнопки
        eraseButton.addActionListener(e -> {
            eraseAll(TBPanel); // Очищаем логи при нажатии
        });
        bottomPanel.add(eraseButton);
        bottomPanel.add(createButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Центрирование окна
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void eraseTBFunc(JPanel panel, int id) {
        dbTools.deleteTBFunctionById(id);
        updatePanel(panel);
    }

    private void eraseAll(JPanel panel) {
        dbTools.deleteAllTBFunctions();
        updatePanel(panel);
    }

    private void createTBFunc(JPanel panel) {
        SettingsWindowChooseTheWayCreateTF window = new SettingsWindowChooseTheWayCreateTF(owner, factoryService);
        window.setVisible(true);
        updatePanel(panel);
    }

    private void updatePanel(JPanel panel) {
        // Очищаем содержимое панели
        panel.removeAll();

        // Загружаем пользователей из базы данных
        Map<Integer, TBFunc> tbFunctions = dbTools.getAllTBFunctions();

        // Проверяем, есть ли пользователи для отображения
        if (tbFunctions.isEmpty()) {
            // Если пользователей нет, отображаем сообщение
            JLabel noUsersLabel = new JLabel("Нет сохранённых табулированных функций");
            noUsersLabel.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(16f));
            noUsersLabel.setForeground(ConstantColors.DARK_GREEN);
            noUsersLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(noUsersLabel, BorderLayout.CENTER);
        } else {
            // Если пользователи есть, создаем панель с их данными
            JPanel tbPanel = new JPanel();
            tbPanel.setLayout(new BoxLayout(tbPanel, BoxLayout.Y_AXIS));
            tbPanel.setBackground(ConstantColors.BLACK);

            for (Integer id : tbFunctions.keySet()) {
                JPanel TBPanel = new JPanel();
                TBPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                TBPanel.setBackground(ConstantColors.BLACK);
                StringBuilder tbPoints = new StringBuilder();
                for (int i = 0; i < tbFunctions.get(id).getXValues().length; i++) {
                    tbPoints.append("( ")
                            .append(tbFunctions.get(id).getXValues()[i])
                            .append(", ")
                            .append(tbFunctions.get(id).getYValues()[i])
                            .append("), ");
                }
                tbPoints.deleteCharAt(tbPoints.length() - 1);
                tbPoints.deleteCharAt(tbPoints.length() - 1);
                JLabel tbInfo = new JLabel(tbPoints.toString());
                tbInfo.setForeground(ConstantColors.DARK_GREEN);
                tbInfo.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(14f));
                tbPanel.add(tbInfo);
                // Кнопка "Delete" для удаления пользователя
                JButton deleteButton = new JButton("Удалить");
                deleteButton.setBackground(Color.RED);
                deleteButton.setForeground(ConstantColors.THISTLE);
                deleteButton.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(12f));
                deleteButton.setFocusPainted(false);
                deleteButton.addActionListener(e -> eraseTBFunc(panel, id));
                TBPanel.add(deleteButton);

                // Добавляем панель пользователя в основную панель
                tbPanel.add(TBPanel);
            }

            // Добавляем JScrollPane для прокрутки, если пользователей много
            JScrollPane scrollPane = new JScrollPane(tbPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            panel.add(scrollPane, BorderLayout.CENTER);
        }

        // Обновляем панель
        panel.revalidate();
        panel.repaint();
    }
}
