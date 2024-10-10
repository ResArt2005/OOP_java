package tests;

import functions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//
class CompositeFunctionTest {

    @Test
    void testApply3() {
        IdentityFunction dObj = new IdentityFunction();
        SqrFunction pObj = new SqrFunction();

        IdentityFunction gObj = new IdentityFunction();
        CompositeFunction fObj = new CompositeFunction(dObj, pObj);

        CompositeFunction mainObj = new CompositeFunction(gObj, fObj);

        Assertions.assertEquals(1, mainObj.apply(1));
        Assertions.assertEquals(Math.pow(Math.sqrt(2) , 1), mainObj.apply(2));
        Assertions.assertEquals(Math.pow(Math.sqrt(3) , 1), mainObj.apply(3));
        Assertions.assertEquals(Math.pow(Math.sqrt(10) , 1), mainObj.apply(10));
    }
}