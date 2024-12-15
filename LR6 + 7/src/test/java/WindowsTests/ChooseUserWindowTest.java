package WindowsTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.ChooseUserWindow;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ChooseUserWindowTest {

    @InjectMocks
    private ChooseUserWindow chooseUserWindow;

    @Mock
    private User mockUser;

    @Before
    public void setUp() {
        chooseUserWindow = new ChooseUserWindow();
    }

    @Test
    public void testCreateLogin_Successful() {
        try (MockedStatic<dbTools> mockedDbTools = Mockito.mockStatic(dbTools.class)) {
            String token = "testToken";
            String password = "testPassword";
            when(mockUser.getPassword()).thenReturn(password);
            when(mockUser.getLogin()).thenReturn("testLogin");

            // Замокировать получение пользователей
            Map<String, User> mockUsers = new HashMap<>();
            mockUsers.put(token, mockUser);
            mockedDbTools.when(dbTools::getAllUsers).thenReturn(mockUsers);

            // Установить поля токена и пароля
            chooseUserWindow.tokenField.setText(token);
            chooseUserWindow.passwordField.setText(password);

            // Запустить метод
            chooseUserWindow.attemptLogin();


            //JOptionPane.showMessageDialog(any(), eq("Добро пожаловать, testLogin!"), eq("Успех"), eq(JOptionPane.INFORMATION_MESSAGE));
        }
    }

    @Test
    public void testAttemptLogin_Failed() {
        try (MockedStatic<dbTools> mockedDbTools = Mockito.mockStatic(dbTools.class)) {
            String token = "wrongToken";
            String password = "wrongPassword";

            // Замокировать получение пользователей
            Map<String, User> mockUsers = new HashMap<>();
            mockedDbTools.when(dbTools::getAllUsers).thenReturn(mockUsers);  // Пользователь не найден

            // Установить поля токена и пароля
            chooseUserWindow.tokenField.setText(token);
            chooseUserWindow.passwordField.setText(password);

            // Запустить метод
            chooseUserWindow.attemptLogin();

            //JOptionPane.showMessageDialog(any(), eq("Неверный токен или пароль"), eq("Ошибка"), eq(JOptionPane.ERROR_MESSAGE));
        }
    }

    @Test
    public void testAttemptLogin_Successful() {
        try (MockedStatic<dbTools> mockedDbTools = Mockito.mockStatic(dbTools.class)) {
            String token = "testToken";
            String password = "testPassword";
            when(mockUser.getPassword()).thenReturn(password);
            when(mockUser.getLogin()).thenReturn("testLogin");

            // Замокировать получение пользователей
            Map<String, User> mockUsers = new HashMap<>();
            mockUsers.put(token, mockUser);
            mockedDbTools.when(dbTools::getAllUsers).thenReturn(mockUsers);

            // Установить поля токена и пароля
            chooseUserWindow.tokenField.setText(token);
            chooseUserWindow.passwordField.setText(password);

            // Запустить метод
            chooseUserWindow.attemptLogin();

            //JOptionPane.showMessageDialog(any(), eq("Добро пожаловать, testLogin!"), eq("Успех"), eq(JOptionPane.INFORMATION_MESSAGE));
        }
    }
}
