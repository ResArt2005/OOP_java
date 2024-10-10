package tests;

import functions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//
class CompositeFunctionTest {

    @Test
    void testApply1() {
        SqrFunction gObj = new SqrFunction();
        SquareFunction fObj = new SquareFunction();
        CompositeFunction mainObj = new CompositeFunction(gObj, fObj);
        Assertions.assertEquals(1, mainObj.apply(1));
        Assertions.assertEquals(2, mainObj.apply(2));
        Assertions.assertEquals(8, mainObj.apply(8));
    }
    @Test
    void testApply2() {
        CubeFunction gObj = new CubeFunction();
        SquareFunction fObj = new SquareFunction();
        CompositeFunction mainObj = new CompositeFunction(gObj, fObj);
        Assertions.assertEquals(1, mainObj.apply(1));
        Assertions.assertEquals(64, mainObj.apply(2));
        Assertions.assertEquals(729, mainObj.apply(3));
        Assertions.assertEquals(1000000, mainObj.apply(10));
    }
    @Test
    void testApply3() {
        IdentityFunction dObj = new IdentityFunction();
        SqrFunction pObj = new SqrFunction();

        CubeFunction gObj = new CubeFunction();
        CompositeFunction fObj = new CompositeFunction(dObj, pObj);

        CompositeFunction mainObj = new CompositeFunction(gObj, fObj);

        Assertions.assertEquals(1, mainObj.apply(1));
        Assertions.assertEquals(Math.pow(Math.sqrt(2) , 3), mainObj.apply(2));
        Assertions.assertEquals(Math.pow(Math.sqrt(3) , 3), mainObj.apply(3));
        Assertions.assertEquals(Math.pow(Math.sqrt(10) , 3), mainObj.apply(10));
    }
}