package tests;
import functions.LinkedListTabulatedFunction;
import functions.factory.LinkedListTabulatedFunctionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LinkedListTabulatedFunctionFactoryTest {
    @Test
    void create() {
        LinkedListTabulatedFunctionFactory obj = new LinkedListTabulatedFunctionFactory();
        Assertions.assertInstanceOf(LinkedListTabulatedFunction.class, obj.create(new double[]{1, 2, 3}, new double[]{1, 4, 9}));
    }
}