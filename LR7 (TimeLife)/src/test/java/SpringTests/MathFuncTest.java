package SpringTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.CosFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.SinFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.CompositeFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
public class MathFuncTest {

    @Test
    public void testNoArgsConstructor() {
        MathFunc mathFunc = new MathFunc();
        assertNotNull(mathFunc);
    }

    @Test
    public void testAllArgsConstructor() {
        String name = "testFunction";
        MathFunction function  = new CosFunction();
        MathFunc mathFunc = new MathFunc(name, function);

        assertEquals(name, mathFunc.getName());
        assertEquals(function.getClass().getSimpleName(), mathFunc.getFunction().getClass().getSimpleName());
    }

    @Test
    public void testSettersAndGetters() {
        MathFunc mathFunc = new MathFunc();
        String name = "testFunction";
        MathFunction function = new CosFunction();

        mathFunc.setName(name);
        mathFunc.setFunction(function);

        assertEquals(name, mathFunc.getName());
        assertEquals(function.getClass().getSimpleName(), mathFunc.getFunction().getClass().getSimpleName());
    }

    @Test
    public void testCompositeFunction() {
        MathFunction firstFunction = new CosFunction();
        MathFunction secondFunction = new SinFunction();
        CompositeFunction compositeFunction = new CompositeFunction(firstFunction, secondFunction);

        MathFunc mathFunc = new MathFunc("compositeFunction", compositeFunction);

        assertEquals("compositeFunction", mathFunc.getName());
        assertTrue(mathFunc.getFunction() instanceof CompositeFunction);

        CompositeFunction parsedFunction = (CompositeFunction) mathFunc.getFunction();
        assertEquals(firstFunction.getClass().getSimpleName(), parsedFunction.getFirstFunction().getClass().getSimpleName());
        assertEquals(secondFunction.getClass().getSimpleName(), parsedFunction.getSecondFunction().getClass().getSimpleName());
    }
}
