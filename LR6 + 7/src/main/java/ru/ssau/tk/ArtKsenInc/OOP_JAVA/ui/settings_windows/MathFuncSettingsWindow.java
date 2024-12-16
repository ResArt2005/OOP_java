package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.RoundedLabel;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MathFuncSettingsWindow extends JDialog {
    private final int WIDTH_WINDOW = 600;
    private final int HEIGHT_WINDOW = 400;
    private final JPanel mathFuncPanel = new JPanel(); // Панель для отображения логов
    private JFrame owner;

    public MathFuncSettingsWindow(JFrame frame) {
        super(frame, "Математические функции", true);
        owner = frame;
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Создание и добавление заголовка с закругленными краями
        RoundedLabel headerLabel = new RoundedLabel("Просмотр математических функций", 20, ConstantColors.DEEP_BLUE);
        headerLabel.setBackground(ConstantColors.DARK_LILAC);
        headerLabel.setForeground(ConstantColors.DEEP_BLUE);
        headerLabel.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(24f)); // Увеличенный шрифт для заголовка
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(WIDTH_WINDOW, 50));
        headerLabel.setBorder(BorderFactory.createLineBorder(ConstantColors.DARK_VIOLET, 2)); // Добавляем темно-бордовую обводку рамки
        add(headerLabel, BorderLayout.NORTH);

        mathFuncPanel.setLayout(new BorderLayout());
        mathFuncPanel.setBackground(ConstantColors.BLACK);
        mathFuncPanel.setForeground(ConstantColors.DARK_GREEN);
        updatePanel(mathFuncPanel);
        add(mathFuncPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(ConstantColors.DARK_PURPLE);

        JButton createButton = new JButton("Добавить");
        createButton.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(16f));
        createButton.setBackground(ConstantColors.DEEP_BLUE);
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);

        // Добавляем слушатель для кнопки
        createButton.addActionListener(e -> createMathFunction(mathFuncPanel));

        bottomPanel.add(createButton);
        JButton eraseButton = new JButton("Стереть все функции");
        eraseButton.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(16f));
        eraseButton.setBackground(ConstantColors.DEEP_BLUE);
        eraseButton.setForeground(Color.WHITE);
        eraseButton.setFocusPainted(false);

        // Добавляем слушатель для кнопки
        eraseButton.addActionListener(e -> eraseAll(mathFuncPanel));

        bottomPanel.add(eraseButton);
        add(bottomPanel, BorderLayout.SOUTH); // Добавляем панель с кнопкой внизу
        setVisible(true);
    }
    private void createMathFunction(JPanel panel){
        new CompositeFunctionCreationWindow(owner);
        updatePanel(panel);
    }
    private void eraseMathFunc(JPanel panel, Integer id) {
        dbTools.deleteMathFunctionById(id);
        updatePanel(panel);
    }

    private void eraseAll(JPanel panel) {
        dbTools.deleteAllMathFunctions();
        updatePanel(panel);
    }

    private void updatePanel(JPanel panel) {
        // Очищаем содержимое панели
        panel.removeAll();

        // Загружаем пользователей из базы данных
        Map<Integer, MathFunc> mathFunctions = dbTools.getAllMathFunctions();

        // Проверяем, есть ли пользователи для отображения
        if (mathFunctions.isEmpty()) {
            // Если пользователей нет, отображаем сообщение
            JLabel noUsersLabel = new JLabel("Математических функций в базе не обнаружено");
            noUsersLabel.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(16f));
            noUsersLabel.setForeground(ConstantColors.DARK_GREEN);
            noUsersLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(noUsersLabel, BorderLayout.CENTER);
        } else {
            // Если пользователи есть, создаем панель с их данными
            JPanel usersPanel = new JPanel();
            usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
            usersPanel.setBackground(ConstantColors.BLACK);

            for (Integer id : mathFunctions.keySet()) {
                // Создаем панель для каждого пользователя
                JPanel userPanel = new JPanel();
                userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                userPanel.setBackground(ConstantColors.BLACK);

                // Добавляем данные пользователя
                JLabel userInfo = new JLabel(mathFunctions.get(id).getName());
                userInfo.setForeground(ConstantColors.DARK_GREEN);
                userInfo.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(14f));
                userPanel.add(userInfo);

                // Кнопка "Delete" для удаления пользователя
                JButton deleteButton = new JButton("Удалить");
                deleteButton.setBackground(Color.RED);
                deleteButton.setForeground(Color.WHITE);
                deleteButton.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(12f));
                deleteButton.setFocusPainted(false);
                deleteButton.addActionListener(e -> eraseMathFunc(panel, id));
                userPanel.add(deleteButton);

                // Добавляем панель пользователя в основную панель
                usersPanel.add(userPanel);
            }

            // Добавляем JScrollPane для прокрутки, если пользователей много
            JScrollPane scrollPane = new JScrollPane(usersPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            panel.add(scrollPane, BorderLayout.CENTER);
        }

        // Обновляем панель
        panel.revalidate();
        panel.repaint();
    }
}
