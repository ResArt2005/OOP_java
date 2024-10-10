package tests;

import functions.UnitFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//
class UnitFunctionTest {

    @Test
    void testApply() {
        UnitFunction obj = new UnitFunction();
        Assertions.assertEquals(1, obj.apply(1));
        Assertions.assertEquals(1, obj.apply(4));
        Assertions.assertEquals(1, obj.apply(8888));
        Assertions.assertEquals(1, obj.apply(42));
    }
}