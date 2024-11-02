import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.function.Executable;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent.SynchronizedTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.Point;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.SqrFunction;
import org.junit.jupiter.api.Assertions;

import java.util.Iterator;
import java.util.NoSuchElementException;

class SynchronizedTabulatedFunctionTest {
    //0, 2.5, 5, 7.5, 10
    private final SynchronizedTabulatedFunction syncObj = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(new SqrFunction(), 0, 10, 5));

    @Test
    void getCount() {
        Assertions.assertEquals(5, syncObj.getCount());
    }

    @Test
    void getX() {
        Assertions.assertEquals(5, syncObj.getX(2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> syncObj.getX(1000));
    }

    @Test
    void getY() {
        Assertions.assertEquals(2.236, syncObj.getY(2), 0.001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> syncObj.getY(1000));
    }

    @Test
    void setY() {
        syncObj.setY(3, 3);
        Assertions.assertEquals(3, syncObj.getY(3));
        Assertions.assertThrows(IllegalArgumentException.class, () -> syncObj.setY(1000, 8));
    }

    @Test
    void indexOfX() {
        Assertions.assertEquals(1, syncObj.indexOfX(2.5));
        Assertions.assertEquals(2, syncObj.indexOfX(5));
        Assertions.assertEquals(-1, syncObj.indexOfX(1000));
    }

    @Test
    void indexOfY() {
        Assertions.assertEquals(0, syncObj.indexOfY(0));
        Assertions.assertEquals(2, syncObj.indexOfY(2.23606797749979));
        syncObj.setY(4, 32);
        Assertions.assertEquals(4, syncObj.indexOfY(32));
        Assertions.assertEquals(-1, syncObj.indexOfY(1000));
    }

    @Test
    void leftBound() {
        Assertions.assertEquals(0, syncObj.leftBound());
    }

    @Test
    void rightBound() {
        Assertions.assertEquals(10, syncObj.rightBound());
    }

    @Test
    void apply() {
        //0, 2.5, 5, 7.5, 10
        Assertions.assertEquals(-1.897, syncObj.apply(-3), 0.001);
        Assertions.assertEquals(3.501, syncObj.apply(12), 0.001);
        Assertions.assertEquals(2.236, syncObj.apply(5), 0.001);
        Assertions.assertEquals(1.712, syncObj.apply(3), 0.001);
    }
    @Test
    void TestIteratorLinkedList(){
        Iterator<Point> iterator = syncObj.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            Assertions.assertEquals(syncObj.getX(i), point.x);
            Assertions.assertEquals(syncObj.getY(i), point.y);
            ++i;
        }
        Assertions.assertThrows(NoSuchElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                iterator.next();
            }
        });
    }
    @Test
    void allMethodsInAROW() {
        Assertions.assertEquals(5, syncObj.getCount());
        Assertions.assertEquals(5, syncObj.getX(2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> syncObj.getX(1000));
        Assertions.assertEquals(2.236, syncObj.getY(2), 0.001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> syncObj.getY(1000));
        syncObj.setY(3, 3);
        Assertions.assertEquals(3, syncObj.getY(3));
        Assertions.assertThrows(IllegalArgumentException.class, () -> syncObj.setY(1000, 8));
        Assertions.assertEquals(1, syncObj.indexOfX(2.5));
        Assertions.assertEquals(2, syncObj.indexOfX(5));
        Assertions.assertEquals(-1, syncObj.indexOfX(1000));
        Assertions.assertEquals(0, syncObj.indexOfY(0));
        Assertions.assertEquals(2, syncObj.indexOfY(2.23606797749979));
        syncObj.setY(4, 32);
        Assertions.assertEquals(4, syncObj.indexOfY(32));
        Assertions.assertEquals(-1, syncObj.indexOfY(1000));
        Assertions.assertEquals(0, syncObj.leftBound());
        Assertions.assertEquals(10, syncObj.rightBound());
        Assertions.assertEquals(-1.897, syncObj.apply(-3), 0.001);
        Assertions.assertEquals(55.199, syncObj.apply(12), 0.001);
        Assertions.assertEquals(2.236, syncObj.apply(5), 0.001);
        Assertions.assertEquals(1.712, syncObj.apply(3), 0.001);
    }

    @Test
    void testGetCount() {
        Integer count = syncObj.doSynchronously(SynchronizedTabulatedFunction::getCount);
        Assertions.assertEquals(5, count);
    }

    @Test
    void testSetY() {
        syncObj.doSynchronously(func -> {
            func.setY(0, 50);
            return null;
        });

        double newY = syncObj.doSynchronously(func -> func.getY(0));
        Assertions.assertEquals(50, newY);
    }

    @Test
    void testGetYAfterUpdate() {
        syncObj.doSynchronously(func -> {
            func.setY(1, 100);
            return null;
        });

        double result = syncObj.doSynchronously(func -> func.getY(1));
        Assertions.assertEquals(100, result);
    }
}