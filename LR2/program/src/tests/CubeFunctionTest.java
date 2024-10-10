package tests;

import functions.CubeFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//
class CubeFunctionTest {

    @Test
    void testApply() {
        CubeFunction obj = new CubeFunction();
        Assertions.assertEquals(1, obj.apply(1));
        Assertions.assertEquals(27, obj.apply(3));
        Assertions.assertEquals(1000, obj.apply(10));
    }
}