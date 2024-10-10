package tests;

import functions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//
class MethodChapliyginaFunctionTest {

    @Test
    void test_1() {
        SumEquation equation = new SumEquation(new StandardFunction[] { new X(), new X(3, 2), new cos(2), new sin(5), new tg(5), new ctg(9)});
        MethodChapliyginaFunction obj = new MethodChapliyginaFunction(equation, 2, new double[]{-3, -2, -1, 0, 1,2,3}, new double[]{9, 4, 1, 0, 1, 4, 9});
        Assertions.assertEquals(-33.394, obj.apply(1), 0.001);
        Assertions.assertEquals(4, obj.apply(2.5), 0.001);
        Assertions.assertEquals(35.906, obj.apply(3), 0.001);
        Assertions.assertEquals(35.906, obj.apply(55), 0.001);
        Assertions.assertTrue(Double.isInfinite(obj.apply(0)));
        Assertions.assertTrue(Double.isNaN(obj.apply(-1)));
    }
    @Test
    void test_2() {
        SumEquation equation = new SumEquation(new StandardFunction[] { new X()});
        MethodChapliyginaFunction obj = new MethodChapliyginaFunction(equation, 2, new double[]{-3, -2, -1, 0, 1,2,3}, new double[]{9, 4, 1, 0, 1, 4, 9});
        Assertions.assertEquals(-0.346, obj.apply(1), 0.001);
        Assertions.assertEquals(4, obj.apply(2.5), 0.001);
        Assertions.assertEquals(10.202, obj.apply(3), 0.001);
        Assertions.assertEquals(10.202, obj.apply(55), 0.001);
        Assertions.assertTrue(Double.isInfinite(obj.apply(0)));
        Assertions.assertTrue(Double.isNaN(obj.apply(-1)));
    }
}