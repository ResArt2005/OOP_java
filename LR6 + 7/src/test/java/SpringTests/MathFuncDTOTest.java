package SpringTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.MathFuncDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)

public class MathFuncDTOTest {

    @Test
    public void testNoArgsConstructor() {
        MathFuncDTO mathFuncDTO = new MathFuncDTO();
        assertNotNull(mathFuncDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        Integer id = 1;
        String name = "testFunction";
        MathFunction function = (x) -> x * x; // Пример математической функции
        MathFuncDTO mathFuncDTO = new MathFuncDTO(id, name, function);

        assertEquals(id, mathFuncDTO.getId());
        assertEquals(name, mathFuncDTO.getName());
        assertEquals(function, mathFuncDTO.getFunction());
    }

    @Test
    public void testSettersAndGetters() {
        MathFuncDTO mathFuncDTO = new MathFuncDTO();
        Integer id = 1;
        String name = "testFunction";
        MathFunction function = (x) -> x * x; // Пример математической функции

        mathFuncDTO.setId(id);
        mathFuncDTO.setName(name);
        mathFuncDTO.setFunction(function);

        assertEquals(id, mathFuncDTO.getId());
        assertEquals(name, mathFuncDTO.getName());
        assertEquals(function, mathFuncDTO.getFunction());
    }
}
