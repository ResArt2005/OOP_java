package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.RoundedLabel;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import javax.swing.*;
import java.awt.*;

public class WorkWithDBWindow extends JDialog {
    private final int WIDTH_WINDOW = 600;
    private final int HEIGHT_WINDOW = 400;
    JFrame owner;

    public WorkWithDBWindow(JFrame frame) {
        super(frame, "Взаимодействие с базой данных", true);
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);
        // Создание и добавление заголовка с закругленными краями
        RoundedLabel headerLabel = new RoundedLabel("Добро пожаловать в панель администрирования", 20, ConstantColors.RED_VIOLET);
        headerLabel.setBackground(ConstantColors.DARK_LILAC);
        headerLabel.setForeground(ConstantColors.RED_VIOLET);
        headerLabel.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(24f)); // Увеличенный шрифт для заголовка
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(WIDTH_WINDOW, 50));
        headerLabel.setBorder(BorderFactory.createLineBorder(ConstantColors.DARK_VIOLET, 2)); // Добавляем темно-бордовую обводку рамки
        add(headerLabel, BorderLayout.NORTH);

        // Панель для кнопок с центральным размещением
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));  // Сетка 5 на 1 с отступами
        buttonPanel.setBackground(ConstantColors.RICH_PURPLE);
        //----------------------------------------------------
        JButton UserButton = createRoundedButton("Пользователи", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.THISTLE, new Cursor(Cursor.HAND_CURSOR));
        UserButton.addActionListener(e -> openUserSettingsWindow());
        //----------------------------------------------------
        JButton MathFuncButton = createRoundedButton("Математические функции", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.THISTLE, new Cursor(Cursor.HAND_CURSOR));
        MathFuncButton.addActionListener(e -> openMathFuncSettingsWindow());
        //----------------------------------------------------
        JButton TBFuncButton = createRoundedButton("Табулированные функции", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.THISTLE, new Cursor(Cursor.HAND_CURSOR));
        TBFuncButton.addActionListener(e -> openTBSettingsWindow());
        //----------------------------------------------------
        JButton LogButton = createRoundedButton("Посмотреть логи", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.THISTLE, new Cursor(Cursor.HAND_CURSOR));
        LogButton.addActionListener(e -> openLogsSettingsWindow());
        //----------------------------------------------------
        JButton overWrite = createRoundedButton("Стереть все данные в базе", ConstantFonts.Open_Sans_Bold, ConstantColors.RICH_PURPLE, ConstantColors.THISTLE, new Cursor(Cursor.HAND_CURSOR));
        overWrite.addActionListener(e -> Erase());
        //----------------------------------------------------
        buttonPanel.add(UserButton);
        buttonPanel.add(MathFuncButton);
        buttonPanel.add(TBFuncButton);
        buttonPanel.add(LogButton);
        buttonPanel.add(overWrite);
        add(buttonPanel, BorderLayout.CENTER);

        // Центрирование окна
        setLocationRelativeTo(null);

        setVisible(true);
    }

    // Метод для создания кнопки с краями
    private JButton createRoundedButton(String text, Font font, Color background, Color foreground, Cursor cursor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setCursor(cursor);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ConstantColors.DARK_VIOLET, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        button.setContentAreaFilled(false);
        return button;
    }

    private void openUserSettingsWindow() {

    }

    private void openMathFuncSettingsWindow() {

    }

    private void openTBSettingsWindow() {

    }

    private void openLogsSettingsWindow() {

    }

    private void Erase() {
        dbTools.deleteAllLogs();
        dbTools.deleteAllTBFunctions();
        dbTools.deleteAllMathFunctions();
        dbTools.deleteAllUsers();
        dbTools.addAdmin();
        showSuccessDialog("База чиста!");
    }

    private void showSuccessDialog(String message) {
        // Создаем кастомное диалоговое окно
        JDialog dialog = new JDialog(this, "Успех", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // Устанавливаем фон и шрифт для диалогового окна
        dialog.getContentPane().setBackground(ConstantColors.DEEP_PURPLE);
        dialog.setFont(ConstantFonts.Open_Sans_Bold);

        // Создаем панель для содержимого диалогового окна
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ConstantColors.DEEP_PURPLE);

        // Создаем метку с сообщением об успехе
        JLabel messageLabel = new JLabel(message, JLabel.CENTER);
        messageLabel.setForeground(ConstantColors.THISTLE);
        messageLabel.setFont(ConstantFonts.Open_Sans_Bold);

        // Добавляем метку на панель
        panel.add(messageLabel, BorderLayout.CENTER);

        // Создаем кнопку OK
        JButton okButton = new JButton("OK");
        okButton.setBackground(ConstantColors.RICH_PURPLE);
        okButton.setForeground(ConstantColors.THISTLE);
        okButton.setFont(ConstantFonts.Open_Sans_Bold);
        okButton.setFocusPainted(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Добавляем кнопку на панель
        panel.add(okButton, BorderLayout.SOUTH);

        // Добавляем панель на диалоговое окно
        dialog.add(panel);

        // Обработчик событий для кнопки OK
        okButton.addActionListener(e -> dialog.dispose());

        // Делаем диалоговое окно видимым
        dialog.setVisible(true);
    }
}
