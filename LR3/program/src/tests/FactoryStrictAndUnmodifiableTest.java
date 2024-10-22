package tests;

import functions.LinkedListTabulatedFunction;
import functions.SqrFunction;
import functions.StrictTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.LinkedListTabulatedFunctionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FactoryStrictAndUnmodifiableTest {
    LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();

    @Test
    void createStrict() {
        TabulatedFunction obj_LinkedList = factory.createStrict(new double[]{1, 2, 3}, new double[]{1, 4, 9});
        Assertions.assertThrows(UnsupportedOperationException.class, () -> obj_LinkedList.apply(5));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> obj_LinkedList.apply(1.5));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> obj_LinkedList.apply(-1));
    }

    @Test
    void createUnmodifiable() {
    }

    @Test
    void createStrictUnmodifiable() {
    }
}