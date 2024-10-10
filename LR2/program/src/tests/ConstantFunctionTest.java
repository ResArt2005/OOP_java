package tests;

import org.junit.jupiter.api.Test;
import functions.ConstantFunction;
import org.junit.jupiter.api.Assertions;
//
class ConstantFunctionTest {
    private final ConstantFunction obj = new ConstantFunction(42);
    @Test
    void getConstant() {
        Assertions.assertEquals(42, obj.getConstant());
    }

    @Test
    void apply() {
        Assertions.assertEquals(42, obj.apply(1));
        Assertions.assertEquals(42, obj.apply(4));
        Assertions.assertEquals(42, obj.apply(8888));
        Assertions.assertEquals(42, obj.apply(42));
    }
}