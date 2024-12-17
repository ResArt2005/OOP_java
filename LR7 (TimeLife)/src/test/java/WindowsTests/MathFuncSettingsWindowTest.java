package WindowsTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.UserDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.MainWindow;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows.MathFuncSettingsWindow;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = Main.class)
public class MathFuncSettingsWindowTest {
    private MathFuncSettingsWindow mSettingsWindow;
    MainWindow owner = new MainWindow(new UserDTO(0, "admin", "admin"));

    @Before
    public void setUp() {
        try (MockedStatic<dbTools> mockedDbTools = Mockito.mockStatic(dbTools.class)) {
            mSettingsWindow = new MathFuncSettingsWindow(owner, true);
        }
    }

    @Test
    public void testEraseAll() {
        // Мокируем статические методы класса dbTools
        try (MockedStatic<dbTools> mockedDbTools = Mockito.mockStatic(dbTools.class)) {
            mSettingsWindow.eraseAll();
        }
    }
    @Test
    public void create(){
        // Мокируем статические методы класса dbTools
        try (MockedStatic<dbTools> mockedDbTools = Mockito.mockStatic(dbTools.class)) {
            mSettingsWindow.createMathFunction();
        }
    }
}
