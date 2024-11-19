import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.myOwnFunctionsForEquation.MathDerivativeAndIntegral;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.myOwnFunctionsForEquation.SumEquationFunction;

//
class MethodChapliyginaFunctionTest {

    @Test
    void testApplyChap1() {
        SumEquationFunction equation = new SumEquationFunction(new MathDerivativeAndIntegral[] { new XFunction(), new XFunction(3, 2), new CosFunction(2), new SinFunction(5), new TgFunction(5), new CtgFunction(9)});
        MethodChapliyginaFunction obj = new MethodChapliyginaFunction(equation, 2, new double[]{-3, -2, -1, 0, 1,2,3}, new double[]{9, 4, 1, 0, 1, 4, 9});
        Assertions.assertEquals(-33.394, obj.apply(1), 0.001);
        Assertions.assertEquals(4, obj.apply(2.5), 0.001);
        Assertions.assertEquals(35.906, obj.apply(3), 0.001);
        Assertions.assertEquals(35.906, obj.apply(55), 0.001);
        Assertions.assertTrue(Double.isInfinite(obj.apply(0)));
        Assertions.assertTrue(Double.isNaN(obj.apply(-1)));
    }
    @Test
    void testApplyChap2() {
        SumEquationFunction equation = new SumEquationFunction(new MathDerivativeAndIntegral[] { new XFunction()});
        MethodChapliyginaFunction obj = new MethodChapliyginaFunction(equation, 2, new double[]{-3, -2, -1, 0, 1,2,3}, new double[]{9, 4, 1, 0, 1, 4, 9});
        Assertions.assertEquals(-0.346, obj.apply(1), 0.001);
        Assertions.assertEquals(4, obj.apply(2.5), 0.001);
        Assertions.assertEquals(10.202, obj.apply(3), 0.001);
        Assertions.assertEquals(10.202, obj.apply(55), 0.001);
        Assertions.assertTrue(Double.isInfinite(obj.apply(0)));
        Assertions.assertTrue(Double.isNaN(obj.apply(-1)));
    }
}