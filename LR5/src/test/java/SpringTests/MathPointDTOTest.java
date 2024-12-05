package SpringTests;

import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.MathPointDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = Main.class)
public class MathPointDTOTest {

    @Test
    public void testMathFuncDTOConstructor() {
        int id = 1;
        double x = 1.0;
        double y = 2.0;
        MathPointDTO mathPointDTO = new MathPointDTO(id, x, y);

        assertEquals(id, mathPointDTO.getId());
        assertEquals(x, mathPointDTO.getX());
        assertEquals(y, mathPointDTO.getY());
    }

    @Test
    public void testMathFuncDTOSettersAndGetters() {
        MathPointDTO mathPointDTO = new MathPointDTO();
        int id = 1;
        double x = 1.0;
        double y = 2.0;

        mathPointDTO.setId(id);
        mathPointDTO.setX(x);
        mathPointDTO.setY(y);

        assertEquals(id, mathPointDTO.getId());
        assertEquals(x, mathPointDTO.getX());
        assertEquals(y, mathPointDTO.getY());
    }
}
