package BasicTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.LinkedListTabulatedFunctionFactory;
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
        TabulatedFunction obj_LinkedList = factory.createUnmodifiable(new double[]{1, 2, 3}, new double[]{1, 4, 9});
        Assertions.assertThrows(UnsupportedOperationException.class, () -> obj_LinkedList.setY(0, 111));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> obj_LinkedList.setY(1, 222));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> obj_LinkedList.setY(2, 333));
    }

    @Test
    void createStrictUnmodifiable() {
        TabulatedFunction obj_LinkedList = factory.createStrictUnmodifiable(new double[]{1, 2, 3}, new double[]{1, 4, 9});
        Assertions.assertThrows(UnsupportedOperationException.class, () -> obj_LinkedList.apply(5));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> obj_LinkedList.apply(1.5));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> obj_LinkedList.apply(-1));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> obj_LinkedList.setY(0, 111));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> obj_LinkedList.setY(1, 222));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> obj_LinkedList.setY(2, 333));
    }
}