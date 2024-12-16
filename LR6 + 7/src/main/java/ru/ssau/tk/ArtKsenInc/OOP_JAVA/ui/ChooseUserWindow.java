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
import java.util.Map;

public class ChooseUserWindow extends JFrame {
    private final int WIDTH_WINDOW = 400; // Ширина окна
    private final int HEIGHT_WINDOW = 300; // Высота окна
    public JTextField tokenField;
    public JPasswordField passwordField;

    public ChooseUserWindow() {
        setTitle("Окно входа");
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        // Установка фона и шрифта для всего окна
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Поле для ввода токена
        tokenField = new JTextField();
        add(new RoundedLabel("Введите ваш токен:", 10));
        add(tokenField);

        // Поле для ввода пароля
        passwordField = new JPasswordField();
        add(new RoundedLabel("Введите ваш пароль:", 10));
        add(passwordField);

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
        add(buttonPanel);
        setVisible(true);
    }

    public void attemptLogin() {
        String token = tokenField.getText();
        String password = new String(passwordField.getPassword());

        // Проверка на заполнение полей
        if (token.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, заполните все поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Получение всех пользователей и проверка
        Map<String, User> users = dbTools.getAllUsers();
        User user = users.get(token);

        if (user == null || !user.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(this, "Неверный токен или пароль", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } else {
            // Успешный вход
            JOptionPane.showMessageDialog(this, "Добро пожаловать, " + user.getLogin() + "!", "Успех", JOptionPane.INFORMATION_MESSAGE);

            // Закрытие окна входа
            this.dispose();

            // Открытие главного окна
            SwingUtilities.invokeLater(MainWindow::new);
        }
    }

    public void createNewUser() {
        // Генерация нового пользователя
        User newUser = new User("Unknown", "password");

        // Создание панели для ввода логина и пароля
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField loginField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // Создание метки для токена с возможностью копирования
        JLabel tokenLabel = new JLabel("Ваш токен: " + newUser.getToken());
        tokenLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Добавление MouseListener для копирования токена
        tokenLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                StringSelection selection = new StringSelection(newUser.getToken());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, null);
                JOptionPane.showMessageDialog(panel, "Токен скопирован в буфер обмена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(tokenLabel);
        panel.add(new JLabel("Введите логин:"));
        panel.add(loginField);
        panel.add(new JLabel("Введите пароль:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Создание нового пользователя", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());

            if (!login.isEmpty() && !password.isEmpty()) {
                newUser.setLogin(login);
                newUser.setPassword(password);
                dbTools.createUser(newUser);
                JOptionPane.showMessageDialog(this, "Новый пользователь создан!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Логин и пароль не могут быть пустыми", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(ConstantFonts.Open_Sans_Bold);
        button.setBackground(ConstantColors.RICH_PURPLE);
        button.setForeground(ConstantColors.TIFFANY_BLUE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Pointer при наведении
        return button;
    }
}
