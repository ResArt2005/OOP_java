package BasicTests;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.Point;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.SqrFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.ArrayTabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


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
        Assertions.assertThrows(NoSuchElementException.class, iterator::next);
    }
    @Test
    void TestIteratorArray(){
        ArrayTabulatedFunction obj = new ArrayTabulatedFunction(new SqrFunction(), 0, 10, 5);
        Iterator<Point> iterator = obj.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            Assertions.assertEquals(obj.getX(i), point.x);
            Assertions.assertEquals(obj.getY(i), point.y);
            ++i;
        }
        Assertions.assertThrows(NoSuchElementException.class, iterator::next);
    }
}
