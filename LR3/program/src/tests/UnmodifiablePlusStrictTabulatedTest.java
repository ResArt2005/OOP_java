package tests;

import functions.LinkedListTabulatedFunction;
import functions.SqrFunction;
import functions.StrictTabulatedFunction;
import functions.UnmodifiableTabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class UnmodifiablePlusStrictTabulatedTest {
    StrictTabulatedFunction obj = new StrictTabulatedFunction(new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(new SqrFunction(), 0, 10, 5)));
    @Test
    void testApplyChanges(){
        Assertions.assertThrows(UnsupportedOperationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                obj.setY(3, 999);
            }
        });
    }
    @Test
    void testTryInterpolate(){
        Assertions.assertThrows(UnsupportedOperationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                obj.apply(15);
            }
        });
    }
}
