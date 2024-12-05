package SpringTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MathFuncTest {

    @Test
    public void testMathFuncConstructor() {
        Double x = 1.0;
        Double y = 2.0;
        MathFunc mathFunc = new MathFunc(x, y);

        assertEquals(x, mathFunc.getX());
        assertEquals(y, mathFunc.getY());
    }

    @Test
    public void testMathFuncSettersAndGetters() {
        MathFunc mathFunc = new MathFunc();
        Double x = 1.0;
        Double y = 2.0;

        mathFunc.setX(x);
        mathFunc.setY(y);

        assertEquals(x, mathFunc.getX());
        assertEquals(y, mathFunc.getY());
    }
}
