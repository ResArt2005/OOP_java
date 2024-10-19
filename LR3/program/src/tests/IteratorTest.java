package tests;

import java.util.Iterator;
import java.util.NoSuchElementException;

import functions.Point;
import functions.SqrFunction;
import functions.LinkedListTabulatedFunction;
import functions.ArrayTabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


public class IteratorTest {
    @Test
    void TestIteratorLinkedList(){
        LinkedListTabulatedFunction obj = new LinkedListTabulatedFunction(new SqrFunction(), 0, 10, 5);
        Iterator<Point> iterator = obj.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            Assertions.assertEquals(obj.getX(i), point.x);
            Assertions.assertEquals(obj.getY(i), point.y);
            ++i;
        }
        Assertions.assertThrows(NoSuchElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                iterator.next();
            }
        });
    }

}
