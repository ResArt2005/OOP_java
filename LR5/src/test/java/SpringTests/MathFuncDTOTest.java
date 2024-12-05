package SpringTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.MathFuncDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MathFuncDTOTest {

    @Test
    public void testMathFuncDTOConstructor() {
        int id = 1;
        double x = 1.0;
        double y = 2.0;
        MathFuncDTO mathFuncDTO = new MathFuncDTO(id, x, y);

        assertEquals(id, mathFuncDTO.getId());
        assertEquals(x, mathFuncDTO.getX());
        assertEquals(y, mathFuncDTO.getY());
    }

    @Test
    public void testMathFuncDTOSettersAndGetters() {
        MathFuncDTO mathFuncDTO = new MathFuncDTO();
        int id = 1;
        double x = 1.0;
        double y = 2.0;

        mathFuncDTO.setId(id);
        mathFuncDTO.setX(x);
        mathFuncDTO.setY(y);

        assertEquals(id, mathFuncDTO.getId());
        assertEquals(x, mathFuncDTO.getX());
        assertEquals(y, mathFuncDTO.getY());
    }
}
