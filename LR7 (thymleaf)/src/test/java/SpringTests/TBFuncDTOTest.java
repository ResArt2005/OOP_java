package SpringTests;

import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.TBFuncDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)

public class TBFuncDTOTest {

    @Test
    public void testNoArgsConstructor() {
        TBFuncDTO tbFuncDTO = new TBFuncDTO();
        assertNotNull(tbFuncDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        int id = 1;
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        TBFuncDTO tbFuncDTO = new TBFuncDTO(xValues, yValues);

        assertArrayEquals(xValues, tbFuncDTO.getXValues());
        assertArrayEquals(yValues, tbFuncDTO.getYValues());
    }

    @Test
    public void testSettersAndGetters() {
        TBFuncDTO tbFuncDTO = new TBFuncDTO();
        int id = 1;
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};


        tbFuncDTO.setXValues(xValues);
        tbFuncDTO.setYValues(yValues);


        assertArrayEquals(xValues, tbFuncDTO.getXValues());
        assertArrayEquals(yValues, tbFuncDTO.getYValues());
    }
}
