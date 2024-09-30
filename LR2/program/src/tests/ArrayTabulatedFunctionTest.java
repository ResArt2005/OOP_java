package tests;

import functions.ArrayTabulatedFunction;
import functions.SqrFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;//Для тестирования защищённых и приватных полей

class ArrayTabulatedFunctionTest {
    private SqrFunction fun = new SqrFunction();
    private double StartX = 0;
    private double EndX = 10;
    private int count = 5;
    private ArrayTabulatedFunction obj = new ArrayTabulatedFunction(fun, EndX, StartX, count);

    @Test
    void getCount() {
        System.out.println(obj.getCount());
    }

    @Test
    void getX() {
        for (int i = 0; i < obj.getCount(); ++i) {
            System.out.print(obj.getX(i) + " ");
        }
        System.out.println();
    }

    @Test
    void getY() {
        for (int i = 0; i < obj.getCount(); ++i) {
            System.out.print(obj.getY(i) + " ");
        }
        System.out.println();
    }

    @Test
    void setY() {
        obj.setY(3, 3);
        System.out.println(obj.getY(3));
        obj.setY(1000, 2);
        System.out.println(obj.getY(1000));
    }

    @Test
    void indexOfX() {
        System.out.println(obj.indexOfX(2.5));
        System.out.println(obj.indexOfX(5));
        System.out.println(obj.indexOfX(1000));
    }

    @Test
    void indexOfY() {
        System.out.println(obj.indexOfY(0));
        System.out.println(obj.indexOfY(2.23606797749979));
        obj.setY(4, 32);
        System.out.println(obj.indexOfY(32));
        System.out.println(obj.indexOfY(1000));
    }

    @Test
    void leftBound() {
        System.out.println(obj.leftBound());
    }

    @Test
    void rightBound() {
        System.out.println(obj.rightBound());
    }

    @Test
    void apply() {

    }
}