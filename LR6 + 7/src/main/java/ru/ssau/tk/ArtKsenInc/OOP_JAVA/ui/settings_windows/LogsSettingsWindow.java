package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.LogDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.RoundedLabel;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LogsSettingsWindow extends JDialog {
    private final int WIDTH_WINDOW = 600;
    private final int HEIGHT_WINDOW = 400;
    private final JPanel logsPanel = new JPanel(); // Панель для отображения логов
    private JFrame owner;

    public LogsSettingsWindow(JFrame frame) {
        super(frame, "Логи", true);
        owner = frame;
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Создание и добавление заголовка с закругленными краями
        RoundedLabel headerLabel = new RoundedLabel("Просмотр Логов", 20, ConstantColors.DEEP_BLUE);
        headerLabel.setBackground(ConstantColors.DARK_LILAC);
        headerLabel.setForeground(ConstantColors.DEEP_BLUE);
        headerLabel.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(24f)); // Увеличенный шрифт для заголовка
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(WIDTH_WINDOW, 50));
        headerLabel.setBorder(BorderFactory.createLineBorder(ConstantColors.DARK_VIOLET, 2)); // Добавляем темно-бордовую обводку рамки
        add(headerLabel, BorderLayout.NORTH);

        // Панель для отображения логов
        logsPanel.setLayout(new BorderLayout());
        logsPanel.setBackground(ConstantColors.BLACK);
        logsPanel.setForeground(ConstantColors.DARK_GREEN);
        update(logsPanel); // Изначальная загрузка логов
        add(logsPanel, BorderLayout.CENTER);

        // Панель внизу с кнопкой "Стереть логи"
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(ConstantColors.DARK_PURPLE);

        JButton eraseButton = new JButton("Стереть логи");
        eraseButton.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(16f));
        eraseButton.setBackground(ConstantColors.RICH_PURPLE);
        eraseButton.setForeground(ConstantColors.THISTLE);
        eraseButton.setFocusPainted(false);

        // Добавляем слушатель для кнопки
        eraseButton.addActionListener(e -> {
            eraseLogs(logsPanel); // Очищаем логи при нажатии
        });

        bottomPanel.add(eraseButton);
        add(bottomPanel, BorderLayout.SOUTH); // Добавляем панель с кнопкой внизу

        // Центрирование окна
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void eraseLogs(JPanel panel) {
        dbTools.deleteAllLogs(); // Стираем логи в базе данных
        update(panel);           // Обновляем отображение
    }

    private void update(JPanel panel) {
        // Очищаем содержимое панели
        panel.removeAll();

        // Загружаем логи из базы данных
        List<LogDTO> logs = dbTools.showLogs();

        // Проверяем, есть ли логи для отображения
        if (logs.isEmpty()) {
            // Если логов нет, отображаем сообщение
            JLabel noLogsLabel = new JLabel("Нет логов для отображения.");
            noLogsLabel.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(16f));
            noLogsLabel.setForeground(ConstantColors.DARK_GREEN);
            noLogsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(noLogsLabel, BorderLayout.CENTER);
        } else {
            // Если логи есть, создаем JTextArea для их отображения
            JTextArea textArea = new JTextArea(10, 30); // 10 строк, 30 символов в ширину
            textArea.setEditable(false); // Логи только для чтения
            textArea.setBackground(ConstantColors.BLACK);
            textArea.setForeground(ConstantColors.DARK_GREEN);
            textArea.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(14f));

            // Формируем текст для отображения логов
            StringBuilder logText = new StringBuilder();
            for (LogDTO log : logs) {
                logText.append(log.getMessage())
                        .append(" - ")
                        .append(log.getTimestamp())
                        .append("\n"); // Переход на новую строку
            }

            // Устанавливаем текст в JTextArea
            textArea.setText(logText.toString());

            // Добавляем JTextArea в JScrollPane для прокрутки
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            // Добавляем scrollPane на панель
            panel.add(scrollPane, BorderLayout.CENTER);
        }

        // Обновляем панель
        panel.revalidate();
        panel.repaint();
    }
}
