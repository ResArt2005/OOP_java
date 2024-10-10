package tests;

import functions.IdentityFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//
class IdentityFunctionTest {
    @Test
    void testApply() {
        IdentityFunction identityFunction = new IdentityFunction();
        Assertions.assertEquals(1, identityFunction.apply(1));
        Assertions.assertEquals(3.14, identityFunction.apply(3.14));
    }
}