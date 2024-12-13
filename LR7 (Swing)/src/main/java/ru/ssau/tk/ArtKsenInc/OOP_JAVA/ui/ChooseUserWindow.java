package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.RoundedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChooseUserWindow extends JFrame {
    private final int WIDTH_WINDOW = 500; // Ширина окна
    private final int HEIGHT_WINDOW = 300; // Высота окна
    public JTextField tokenField;
    public JPasswordField passwordField;

    public ChooseUserWindow() {
        setTitle("Окно входа");
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Установка фона и шрифта для всего окна
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Поле для ввода токена
        tokenField = new JTextField();
        RoundedLabel tokenLabel = createRoundedLabel("Введите ваш токен", 10, ConstantColors.THISTLE, ConstantColors.THISTLE, ConstantFonts.Open_Sans_Bold, ConstantColors.DEEP_BLUE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(tokenLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tokenField, gbc);

        // Поле для ввода пароля
        passwordField = new JPasswordField();
        RoundedLabel passwordLabel = createRoundedLabel("Введите ваш пароль", 10, ConstantColors.THISTLE, ConstantColors.THISTLE, ConstantFonts.Open_Sans_Bold, ConstantColors.DEEP_BLUE);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Выравнивание по центру
        add(passwordLabel, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField, gbc);

        // Кнопки
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.setBackground(ConstantColors.RICH_PURPLE);

        JButton loginButton = createStyledButton("Войти");
        JButton newUserButton = createStyledButton("Новый пользователь");

        loginButton.addActionListener(e -> attemptLogin());
        newUserButton.addActionListener(e -> createNewUser());

        buttonPanel.add(loginButton);
        buttonPanel.add(newUserButton);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Центрирование окна
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private RoundedLabel createRoundedLabel(String text, int borderRadius, Color borderColor, Color backgroundColor, Font font, Color foregroundColor) {
        RoundedLabel label = new RoundedLabel(text, borderRadius, borderColor);
        label.setBackground(backgroundColor);
        label.setFont(font);
        label.setForeground(foregroundColor);
        label.setOpaque(false); // Устанавливаем непрозрачность в false, чтобы фон был прозрачным
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(ConstantFonts.Open_Sans_Bold);
        button.setBackground(ConstantColors.RICH_PURPLE);
        button.setForeground(ConstantColors.THISTLE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Pointer при наведении
        return button;
    }

    public void attemptLogin() {
        dbTools.addAdmin();
        String token = tokenField.getText();
        String password = new String(passwordField.getPassword());

        // Проверка на заполнение полей
        if (token.isEmpty() || password.isEmpty()) {
            showErrorDialog("Пожалуйста, заполните все поля");
            return;
        }

        // Получение пользователя
        User user = dbTools.getUserByToken(token);

        if (user == null || !user.getPassword().equals(password)) {
            showErrorDialog("Неверный токен или пароль");
        } else {
            // Успешный вход
            showSuccessDialog("Добро пожаловать, " + user.getLogin() + "!");
            dbTools.createLog(user.getLogin() + " совершил вход в систему");
            // Закрытие окна входа
            this.dispose();

            // Открытие главного окна
            SwingUtilities.invokeLater(() -> new MainWindow(dbTools.getUserDTOByToken(token)));
        }
    }

    public void createNewUser() {
        // Генерация нового пользователя
        User newUser = new User("Unknown", "password");
        // Создание панели для ввода логина и пароля
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(ConstantColors.DEEP_PURPLE);
        JTextField loginField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // Создание метки для токена с возможностью копирования
        RoundedLabel tokenLabel = createRoundedLabel(
                "Ваш токен: " + newUser.getToken(),
                5,
                ConstantColors.RICH_PURPLE,
                ConstantColors.PURPLE,
                ConstantFonts.Open_Sans_Bold,
                ConstantColors.DEEP_BLUE
        );
        tokenLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Добавление MouseListener для копирования токена
        tokenLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                StringSelection selection = new StringSelection(newUser.getToken());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, null);
                showSuccessDialog("Токен скопирован в буфер обмена!");
            }
        });

        panel.add(tokenLabel);

        RoundedLabel loginLabel = createRoundedLabel(
                "Введите логин:",
                5,
                ConstantColors.RICH_PURPLE,
                ConstantColors.PURPLE,
                ConstantFonts.Open_Sans_Bold,
                ConstantColors.DEEP_BLUE
        );

        panel.add(loginLabel);
        panel.add(loginField);

        RoundedLabel passwordLabel = createRoundedLabel(
                "Введите пароль:",
                5,
                ConstantColors.RICH_PURPLE,
                ConstantColors.PURPLE,
                ConstantFonts.Open_Sans_Bold,
                ConstantColors.DEEP_BLUE
        );

        panel.add(passwordLabel);
        panel.add(passwordField);

        // Создаем кастомное диалоговое окно
        JDialog dialog = new JDialog(this, "Создание нового пользователя", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(500, 250);
        dialog.setLocationRelativeTo(this);

        // Устанавливаем фон и шрифт для диалогового окна
        dialog.getContentPane().setBackground(ConstantColors.DEEP_PURPLE);
        dialog.setFont(ConstantFonts.Open_Sans_Bold);

        // Создаем панель для содержимого диалогового окна
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setBackground(ConstantColors.DEEP_PURPLE);
        dialogPanel.add(panel, BorderLayout.CENTER);

        // Создаем кнопки OK и Cancel
        JButton okButton = new JButton("OK");
        okButton.setBackground(ConstantColors.RICH_PURPLE);
        okButton.setForeground(ConstantColors.THISTLE);
        okButton.setFont(ConstantFonts.Open_Sans_Bold);
        okButton.setFocusPainted(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(ConstantColors.RICH_PURPLE);
        cancelButton.setForeground(ConstantColors.THISTLE);
        cancelButton.setFont(ConstantFonts.Open_Sans_Bold);
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Создаем панель для кнопок
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.setBackground(ConstantColors.DEEP_PURPLE);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Добавляем панель на диалоговое окно
        dialog.add(dialogPanel);

        // Обработчик событий для кнопки OK
        okButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());

            if (!login.isEmpty() && !password.isEmpty()) {
                newUser.setLogin(login);
                newUser.setPassword(password);
                dbTools.createUser(newUser);
                dbTools.createLog(newUser.getLogin() + " добавился в систему");
                showSuccessDialog("Новый пользователь создан!");
                dialog.dispose();
            } else {
                showErrorDialog("Логин и пароль не могут быть пустыми!");
            }
        });

        // Обработчик событий для кнопки Cancel
        cancelButton.addActionListener(e -> dialog.dispose());

        // Делаем диалоговое окно видимым
        dialog.setVisible(true);
    }

    private void showErrorDialog(String message) {
        // Создаем кастомное диалоговое окно
        JDialog dialog = new JDialog(this, "Ошибка", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(this);

        // Устанавливаем фон и шрифт для диалогового окна
        dialog.getContentPane().setBackground(ConstantColors.DEEP_PURPLE);
        dialog.setFont(ConstantFonts.Open_Sans_Bold);

        // Создаем панель для содержимого диалогового окна
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ConstantColors.DEEP_PURPLE);

        // Создаем метку с сообщением об ошибке
        RoundedLabel messageLabel = createRoundedLabel(message, 20, ConstantColors.RICH_PURPLE, ConstantColors.DEEP_PURPLE, ConstantFonts.Open_Sans_Bold, ConstantColors.THISTLE);

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

    private void showSuccessDialog(String message) {
        // Создаем кастомное диалоговое окно
        JDialog dialog = new JDialog(this, "Успех", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(400, 150);
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