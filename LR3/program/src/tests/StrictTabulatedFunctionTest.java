package tests;

import functions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Iterator;
import java.util.NoSuchElementException;

class StrictTabulatedFunctionTest {
    StrictTabulatedFunction obj_LinkedList = new StrictTabulatedFunction(new LinkedListTabulatedFunction(new SqrFunction(), 0, 10, 5));
    StrictTabulatedFunction obj_Array = new StrictTabulatedFunction(new LinkedListTabulatedFunction(new DeBoorAlgorithmFunction(new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, new double[]{0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100}, 1), 5, 9, 4));
    @Test
    void getCount() {
        Assertions.assertEquals(5, obj_LinkedList.getCount());
        Assertions.assertEquals(4, obj_Array.getCount());
    }

    @Test
    void getX() {
        Assertions.assertEquals(7.5, obj_LinkedList.getX(3));
        Assertions.assertEquals(7.6666, obj_Array.getX(2), 0.0001);
    }

    @Test
    void getY() {
        Assertions.assertEquals(2.7386, obj_LinkedList.getY(3),0.0001);
        Assertions.assertEquals(44.6666, obj_Array.getY(2), 0.0001);
    }

    @Test
    void setY() {
        obj_LinkedList.setY(3, 999);
        obj_Array.setY(2, 999);
        Assertions.assertEquals(999, obj_LinkedList.getY(3));
        Assertions.assertEquals(999, obj_Array.getY(2));
    }

    @Test
    void indexOfX() {
        Assertions.assertEquals(3, obj_LinkedList.indexOfX(7.5));
        Assertions.assertEquals(2, obj_Array.indexOfX(7.6666666666666665));
    }

    @Test
    void indexOfY() {
        Assertions.assertEquals(3, obj_LinkedList.indexOfY(2.7386127875258306));
        Assertions.assertEquals(2, obj_Array.indexOfY(44.66666666666666));
    }

    @Test
    void leftBound() {
        Assertions.assertEquals(0, obj_LinkedList.leftBound());
        Assertions.assertEquals(5, obj_Array.leftBound());
    }

    @Test
    void rightBound() {
        Assertions.assertEquals(10, obj_LinkedList.rightBound());
        Assertions.assertEquals(9, obj_Array.rightBound());
    }

    @Test
    void apply() {
        Assertions.assertEquals(2.7386127875258306, obj_LinkedList.apply(7.5));
        Assertions.assertEquals(44.66666666666666, obj_Array.apply(7.6666666666666665));
    }

    @Test
    void iteratorLinkedList() {
        Iterator<Point> iterator = obj_LinkedList.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            Assertions.assertEquals(obj_LinkedList.getX(i), point.x);
            Assertions.assertEquals(obj_LinkedList.getY(i), point.y);
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
    void iteratorArray() {
        Iterator<Point> iterator = obj_Array.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            Assertions.assertEquals(obj_Array.getX(i), point.x);
            Assertions.assertEquals(obj_Array.getY(i), point.y);
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