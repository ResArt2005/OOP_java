package SpringTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MathPointTest {

    @Test
    public void testMathFuncConstructor() {
        Double x = 1.0;
        Double y = 2.0;
        MathPoint mathPoint = new MathPoint(x, y);

        assertEquals(x, mathPoint.getX());
        assertEquals(y, mathPoint.getY());
    }

    @Test
    public void testMathFuncSettersAndGetters() {
        MathPoint mathPoint = new MathPoint();
        Double x = 1.0;
        Double y = 2.0;

        mathPoint.setX(x);
        mathPoint.setY(y);

        assertEquals(x, mathPoint.getX());
        assertEquals(y, mathPoint.getY());
    }
}
