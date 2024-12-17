package WindowsTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.UserDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.MainWindow;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.WorkWithDBWindow;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import java.util.ArrayList;
import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = Main.class)
public class WorkWithDBWindowTest {
    private WorkWithDBWindow workWithDBWindow;

    MainWindow owner = new MainWindow(new UserDTO(0, "admin", "admin"));

    @Before
    public void setUp() {
        workWithDBWindow = new WorkWithDBWindow(owner, new TabulatedFunctionOperationService(), false);
    }

    @Test
    public void testCreateLogin_Successful() {
        try (MockedStatic<dbTools> mockedDbTools = Mockito.mockStatic(dbTools.class)) {
            mockedDbTools.when(dbTools::getAllUsers).thenReturn(new HashMap<>());
            mockedDbTools.when(dbTools::getAllLogs).thenReturn(new ArrayList<>());
            mockedDbTools.when(dbTools::getAllMathFunctions).thenReturn(new HashMap<>());
            mockedDbTools.when(dbTools::getAllTBFunctions).thenReturn(new HashMap<>());
            // Запустить метод
            workWithDBWindow.Erase();
        }
    }

    @Test
    public void testOpenUserSettingsWindow() {
        try (MockedStatic<dbTools> mockedDbTools = Mockito.mockStatic(dbTools.class)) {
            workWithDBWindow.openUserSettingsWindow();
        }
    }

    @Test
    public void testOpenMathFuncSettingsWindow() {
        try (MockedStatic<dbTools> mockedDbTools = Mockito.mockStatic(dbTools.class)) {
            workWithDBWindow.openMathFuncSettingsWindow();
        }
    }

    @Test
    public void testOpenTBSettingsWindow() {
        try (MockedStatic<dbTools> mockedDbTools = Mockito.mockStatic(dbTools.class)) {
        workWithDBWindow.openTBSettingsWindow();
        }
    }

    @Test
    public void testOpenLogsSettingsWindow() {
        try (MockedStatic<dbTools> mockedDbTools = Mockito.mockStatic(dbTools.class)) {
            workWithDBWindow.openLogsSettingsWindow();
        }
    }
}