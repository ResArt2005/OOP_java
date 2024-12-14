package SpringTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service.UserService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.ChooseUserWindow;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Main.class)
class ChooseUserWindowTest {

    @Autowired
    private UserService userService;

    private ChooseUserWindow chooseUserWindow;
    private JTextField tokenField;
    private JPasswordField passwordField;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        chooseUserWindow = new ChooseUserWindow();

        // Используем рефлексию для доступа к приватным полям
        Field tokenFieldField = ChooseUserWindow.class.getDeclaredField("tokenField");
        tokenFieldField.setAccessible(true);
        tokenField = (JTextField) tokenFieldField.get(chooseUserWindow);

        Field passwordFieldField = ChooseUserWindow.class.getDeclaredField("passwordField");
        passwordFieldField.setAccessible(true);
        passwordField = (JPasswordField) passwordFieldField.get(chooseUserWindow);
    }

    @Test
    void testAttemptLogin_EmptyFields() {
        tokenField.setText("");
        passwordField.setText("");

        chooseUserWindow.attemptLogin();

        // Проверка, что показано сообщение об ошибке
        assertEquals("Пожалуйста, заполните все поля", JOptionPane.showMessageDialog(chooseUserWindow, "Пожалуйста, заполните все поля", "Ошибка", JOptionPane.ERROR_MESSAGE));
    }

    @Test
    void testAttemptLogin_InvalidCredentials() {
        String token = "invalidToken";
        String password = "invalidPassword";
        tokenField.setText(token);
        passwordField.setText(password);

        // Очистка базы данных и создание пользователей
        dbTools.deleteAllUsers();
        userService.create(new User("validLogin", "validPassword"));

        chooseUserWindow.attemptLogin();

        // Проверка, что показано сообщение об ошибке
        assertEquals("Неверный токен или пароль", JOptionPane.showMessageDialog(chooseUserWindow, "Неверный токен или пароль", "Ошибка", JOptionPane.ERROR_MESSAGE));
    }

    @Test
    void testAttemptLogin_ValidCredentials() {
        String token = "validToken";
        String password = "validPassword";
        tokenField.setText(token);
        passwordField.setText(password);

        // Очистка базы данных и создание пользователей
        dbTools.deleteAllUsers();
        User validUser = new User("validLogin", password);
        validUser.setToken(token); // Установка токена
        userService.create(validUser);

        chooseUserWindow.attemptLogin();

        // Проверка, что показано сообщение об успехе
        assertEquals("Добро пожаловать, validLogin!", JOptionPane.showMessageDialog(chooseUserWindow, "Добро пожаловать, validLogin!", "Успех", JOptionPane.INFORMATION_MESSAGE));
    }

    @Test
    void testCreateNewUser_EmptyFields() {
        JPanel panel = new JPanel();
        JTextField loginField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JOptionPane.showConfirmDialog(chooseUserWindow, panel, "Создание нового пользователя", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        chooseUserWindow.createNewUser();

        // Проверка, что показано сообщение об ошибке
        assertEquals("Логин и пароль не могут быть пустыми", JOptionPane.showMessageDialog(chooseUserWindow, "Логин и пароль не могут быть пустыми", "Ошибка", JOptionPane.ERROR_MESSAGE));
    }

    @Test
    void testCreateNewUser_ValidFields() {
        JPanel panel = new JPanel();
        JTextField loginField = new JTextField("newLogin");
        JPasswordField passwordField = new JPasswordField("newPassword");

        User newUser = new User("Unknown", "password");
        JOptionPane.showConfirmDialog(chooseUserWindow, panel, "Создание нового пользователя", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        chooseUserWindow.createNewUser();

        // Проверка, что показано сообщение об успехе
        assertEquals("Новый пользователь создан!", JOptionPane.showMessageDialog(chooseUserWindow, "Новый пользователь создан!", "Успех", JOptionPane.INFORMATION_MESSAGE));
    }
}
