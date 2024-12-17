package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantColors;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.ConstantFonts;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic.RoundedLabel;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class UserSettingsWindow extends JDialog {
    private final int WIDTH_WINDOW = 600;
    private final int HEIGHT_WINDOW = 400;
    private final JPanel userPanel = new JPanel(); // Панель для отображения логов
    private JFrame owner;


    public UserSettingsWindow(JFrame frame) {
        super(frame, "Пользователи", true);
        owner = frame;
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ConstantColors.DARK_PURPLE);

        // Создание и добавление заголовка с закругленными краями
        RoundedLabel headerLabel = new RoundedLabel("Пользователи", 20, ConstantColors.DEEP_BLUE);
        headerLabel.setBackground(ConstantColors.DARK_LILAC);
        headerLabel.setForeground(ConstantColors.DEEP_BLUE);
        headerLabel.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(24f)); // Увеличенный шрифт для заголовка
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(WIDTH_WINDOW, 50));
        headerLabel.setBorder(BorderFactory.createLineBorder(ConstantColors.DARK_VIOLET, 2)); // Добавляем темно-бордовую обводку рамки
        add(headerLabel, BorderLayout.NORTH);

        userPanel.setLayout(new BorderLayout());
        userPanel.setBackground(ConstantColors.BLACK);
        userPanel.setForeground(ConstantColors.DARK_GREEN);
        updatePanel(userPanel);
        add(userPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(ConstantColors.DARK_PURPLE);

        JButton eraseButton = new JButton("Стереть всех и вся");
        eraseButton.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(16f));
        eraseButton.setBackground(ConstantColors.RICH_PURPLE);
        eraseButton.setForeground(ConstantColors.THISTLE);
        eraseButton.setFocusPainted(false);

        // Добавляем слушатель для кнопки
        eraseButton.addActionListener(e -> {
            eraseAll(userPanel); // Очищаем логи при нажатии
        });
        bottomPanel.add(eraseButton);
        JButton createButton = new JButton("Рождение жизни");
        createButton.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(16f));
        createButton.setBackground(ConstantColors.RICH_PURPLE);
        createButton.setForeground(ConstantColors.THISTLE);
        createButton.setFocusPainted(false);

        // Добавляем слушатель для кнопки
        createButton.addActionListener(e -> {
            createNewUser(userPanel); // Очищаем логи при нажатии
        });
        bottomPanel.add(createButton);
        add(bottomPanel, BorderLayout.SOUTH); // Добавляем панель с кнопкой внизу

        // Центрирование окна
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void updateUser(JPanel panel, String userToken, String newLogin, String newPassword) {
        dbTools.updateUserByToken(userToken, newLogin, newPassword);
        updatePanel(panel);
    }

    private void eraseUser(JPanel panel, String userToken) {
        dbTools.deleteUserByToken(userToken);
        updatePanel(panel);
    }

    private void eraseAll(JPanel panel) {
        dbTools.deleteAllUsers();
        updatePanel(panel);
    }

    private void updatePanel(JPanel panel) {
        // Очищаем содержимое панели
        panel.removeAll();

        // Загружаем пользователей из базы данных
        Map<String, User> users = dbTools.getAllUsers();

        // Проверяем, есть ли пользователи для отображения
        if (users.isEmpty()) {
            // Если пользователей нет, отображаем сообщение
            JLabel noUsersLabel = new JLabel("Из пользователей только администратор");
            noUsersLabel.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(16f));
            noUsersLabel.setForeground(ConstantColors.DARK_GREEN);
            noUsersLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(noUsersLabel, BorderLayout.CENTER);
        } else {
            // Если пользователи есть, создаем панель с их данными
            JPanel usersPanel = new JPanel();
            usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
            usersPanel.setBackground(ConstantColors.BLACK);

            for (String token : users.keySet()) {
                // Создаем панель для каждого пользователя
                JPanel userPanel = new JPanel();
                userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                userPanel.setBackground(ConstantColors.BLACK);

                // Добавляем данные пользователя
                JLabel userInfo = new JLabel(users.get(token).getToken() + " - " + users.get(token).getLogin() + " - " + users.get(token).getPassword());
                userInfo.setForeground(ConstantColors.DARK_GREEN);
                userInfo.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(14f));
                userPanel.add(userInfo);

                // Кнопка "Update" для изменения данных пользователя
                JButton updateButton = new JButton("Изменить");
                updateButton.setBackground(ConstantColors.RICH_PURPLE);
                updateButton.setForeground(ConstantColors.THISTLE);
                updateButton.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(12f));
                updateButton.setFocusPainted(false);
                updateButton.addActionListener(e -> showUpdateDialog(panel, token, users.get(token).getLogin(), users.get(token).getPassword()));
                userPanel.add(updateButton);

                // Кнопка "Delete" для удаления пользователя
                JButton deleteButton = new JButton("Удалить");
                deleteButton.setBackground(Color.RED);
                deleteButton.setForeground(ConstantColors.THISTLE);
                deleteButton.setFont(ConstantFonts.Open_Sans_Bold.deriveFont(12f));
                deleteButton.setFocusPainted(false);
                deleteButton.addActionListener(e -> eraseUser(panel, token));
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

    // Метод для отображения диалогового окна с обновлением пользователя
    private void showUpdateDialog(JPanel panel, String userToken, String currentLogin, String currentPassword) {
        // Поля для ввода нового логина и пароля
        JTextField loginField = new JTextField(currentLogin, 15);
        JPasswordField passwordField = new JPasswordField(currentPassword, 15);

        // Панель для диалога
        JPanel dialogPanel = new JPanel(new GridLayout(2, 2));
        dialogPanel.add(new JLabel("Новый логин:"));
        dialogPanel.add(loginField);
        dialogPanel.add(new JLabel("Новый пароль:"));
        dialogPanel.add(passwordField);

        // Показать диалоговое окно для ввода новых данных
        int result = JOptionPane.showConfirmDialog(null, dialogPanel, "Изменения данных пользователя", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Если нажата кнопка OK
        if (result == JOptionPane.OK_OPTION) {
            String newLogin = loginField.getText();
            String newPassword = new String(passwordField.getPassword());
            updateUser(panel, userToken, newLogin, newPassword); // Обновляем пользователя
        }
    }

    // Метод для создания диалогового окна с созданием пользователя
    public void createNewUser(JPanel GlobalPanel) {
        // Генерация нового пользователя
        User newUser = new User("Unknown", "password");
        // Создание панели для ввода логина и пароля
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(ConstantColors.DEEP_PURPLE);
        JTextField loginField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // Создание метки для токена с возможностью копирования
        RoundedLabel tokenLabel = createRoundedLabel(
                "Токен пользователя: " + newUser.getToken(),
                5,
                ConstantColors.RICH_PURPLE,
                ConstantColors.DARK_PURPLE,
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
                ConstantColors.THISTLE,
                ConstantFonts.Open_Sans_Bold,
                ConstantColors.DEEP_BLUE
        );

        panel.add(loginLabel);
        panel.add(loginField);

        RoundedLabel passwordLabel = createRoundedLabel(
                "Введите пароль:",
                5,
                ConstantColors.RICH_PURPLE,
                ConstantColors.THISTLE,
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
                updatePanel(GlobalPanel);
                dialog.dispose();
            } else {
                showErrorDialog("Логин и пароль не могут быть пустыми");
            }
        });

        // Обработчик событий для кнопки Cancel
        cancelButton.addActionListener(e -> dialog.dispose());

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
        messageLabel.setForeground(ConstantColors.DEEP_BLUE);
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
        RoundedLabel messageLabel = createRoundedLabel(message, 20, ConstantColors.RICH_PURPLE, ConstantColors.DEEP_PURPLE, ConstantFonts.Open_Sans_Bold, ConstantColors.DEEP_BLUE);

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

    private RoundedLabel createRoundedLabel(String text, int borderRadius, Color borderColor, Color backgroundColor, Font font, Color foregroundColor) {
        RoundedLabel label = new RoundedLabel(text, borderRadius, borderColor);
        label.setBackground(backgroundColor);
        label.setFont(font);
        label.setForeground(foregroundColor);
        label.setOpaque(false); // Устанавливаем непрозрачность в false, чтобы фон был прозрачным
        return label;
    }
}
