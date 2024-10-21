package tests;

import operations.LeftSteppingDifferentialOperator;
import functions.LinkedListTabulatedFunction;
import functions.MathFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class LeftSteppingDifferentialOperatorTest {
    LeftSteppingDifferentialOperator op = new LeftSteppingDifferentialOperator(2);
    LinkedListTabulatedFunction fun = new LinkedListTabulatedFunction(new double[]{4,9,16}, new double[]{2,3,4});
    @Test
    void derive() {
        MathFunction newFunc = op.derive(fun);
        Assertions.assertEquals(0.2, newFunc.apply(0), 0.1);
        Assertions.assertEquals(0.1999, newFunc.apply(4), 0.0001);
        Assertions.assertEquals(0.1714, newFunc.apply(10), 0.0001);
        Assertions.assertEquals(0.1428571428571428, newFunc.apply(25));
        Assertions.assertEquals(0.14285714285714324, newFunc.apply(90));
        Assertions.assertEquals(0.14285714285714235, newFunc.apply(130));
        Assertions.assertEquals(0.1428571428571388, newFunc.apply(999));
    }
}