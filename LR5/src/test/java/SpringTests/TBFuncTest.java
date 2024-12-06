package SpringTests;

import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.TBFunc;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
public class TBFuncTest {

    @Test
    public void testNoArgsConstructor() {
        TBFunc tbFunc = new TBFunc();
        assertNotNull(tbFunc);
    }

    @Test
    public void testAllArgsConstructor() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        TBFunc tbFunc = new TBFunc(xValues, yValues);

        assertArrayEquals(xValues, tbFunc.getXValues());
        assertArrayEquals(yValues, tbFunc.getYValues());
    }

    @Test
    public void testSettersAndGetters() {
        TBFunc tbFunc = new TBFunc();
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};

        tbFunc.setXValues(xValues);
        tbFunc.setYValues(yValues);

        assertArrayEquals(xValues, tbFunc.getXValues());
        assertArrayEquals(yValues, tbFunc.getYValues());
    }
}
