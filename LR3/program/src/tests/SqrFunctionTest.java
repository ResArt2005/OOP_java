package tests;

import java.lang.Math;
import functions.SqrFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//
class SqrFunctionTest {
    @Test
    void testApply(){
        SqrFunction obj = new SqrFunction();
        Assertions.assertEquals(Math.sqrt(1), obj.apply(1));
        Assertions.assertEquals(Math.sqrt(3.14), obj.apply(3.14));
        Assertions.assertEquals(Math.sqrt(4), obj.apply(4));
    }
}