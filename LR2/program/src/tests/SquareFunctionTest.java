package tests;

import functions.SquareFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//
class SquareFunctionTest {

    @Test
    void testApply() {
        SquareFunction obj = new SquareFunction();
        Assertions.assertEquals(1, obj.apply(1));
        Assertions.assertEquals(9, obj.apply(3));
        Assertions.assertEquals(100, obj.apply(10));
    }
}