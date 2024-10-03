package tests;

import functions.LinkedListTabulatedFunction;
import functions.MathFunction;
import functions.SqrFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTabulatedFunctionTest {
    public class TestClass extends LinkedListTabulatedFunction {
        public TestClass(MathFunction source, double xFrom, double xTo, int count) {
            super(source, xFrom, xTo, count);
        }
        public TestClass(double[] xValues, double[] yValues) {
            super(xValues, yValues);
        }
        public void testfloorIndexOfX(double x) {
            System.out.println(floorIndexOfX(x));
        }

        public void testExtrapolateLeft(double x) {
            System.out.println(extrapolateLeft(x));
        }

        public void testExtrapolateRight(double x) {
            System.out.println(extrapolateRight(x));
        }

        public void testInterpolate(double x, double leftX, double rightX, double leftY, double rightY) {
            System.out.println(interpolate(x, leftX, rightX, leftY, rightY));
        }
        public void testInterpolate(double x, int floorindex) {
            System.out.println(interpolate(x, floorindex));
        }
    }

    private SqrFunction funSqr = new SqrFunction();
    private SqrFunction funCube = new SqrFunction();
    private double StartX = 0;
    private double EndX = 10;
    private int count = 5;
    //0, 2.5, 5, 7.5, 10
    private TestClass obj = new TestClass(funSqr.andThen(funCube), EndX, StartX, count);

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
        System.out.println(obj.getY(3));
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
        //0, 2.5, 5, 7.5, 10
        System.out.println(obj.apply(-3));
        System.out.println(obj.apply(12));
        System.out.println(obj.apply(5));
        System.out.println(obj.apply(3));
    }

    @Test
    public void testInsert() {
        obj.insert(0, 999);
        for (int i = 0; i < obj.getCount(); ++i) {
            System.out.println(obj.getX(i) + "," + obj.getY(i));
        }
        System.out.println();
        obj.insert(3, 999);
        for (int i = 0; i < obj.getCount(); ++i) {
            System.out.println(obj.getX(i) + "," + obj.getY(i));
        }
        System.out.println();
        obj.insert(10, 999);
        for (int i = 0; i < obj.getCount(); ++i) {
            System.out.println(obj.getX(i) + "," + obj.getY(i));
        }
        System.out.println();
        obj.insert(15, 999);
        for (int i = 0; i < obj.getCount(); ++i) {
            System.out.println(obj.getX(i) + "," + obj.getY(i));
        }
        System.out.println();
        obj.insert(12, 111);
        for (int i = 0; i < obj.getCount(); ++i) {
            System.out.println(obj.getX(i) + "," + obj.getY(i));
        }
    }

    @Test
    public void testRemove() {
        obj.remove(0);
        for (int i = 0; i < obj.getCount(); ++i) {
            System.out.println(obj.getX(i));
        }
        System.out.println();
        obj.remove(2);
        for (int i = 0; i < obj.getCount(); ++i) {
            System.out.println(obj.getX(i));
        }
        System.out.println();
        obj.remove(2);
        for (int i = 0; i < obj.getCount(); ++i) {
            System.out.println(obj.getX(i));
        }
    }

    @Test
    void testfloorIndexOfX() {
        //0, 2.5, 5, 7.5, 10
        obj.testfloorIndexOfX(3);
        obj.testfloorIndexOfX(11);
        obj.testfloorIndexOfX(-4);
        obj.testfloorIndexOfX(6);
        obj.testfloorIndexOfX(8);
        obj.testfloorIndexOfX(7.5);
    }
    @Test
    void testExtrapolateLeft() {
        obj.testExtrapolateLeft(2.5);
        obj.testExtrapolateLeft(5);
        obj.testExtrapolateLeft(0);
        obj.testExtrapolateLeft(100);
    }
    @Test
    void testExtrapolateRight() {
        obj.testExtrapolateRight(2.5);
        obj.testExtrapolateRight(5);
        obj.testExtrapolateRight(0);
        obj.testExtrapolateRight(100);
    }
    @Test
    void testInterpolate() {
        obj.testInterpolate(2.5, 0, 3, 1, -4);
        obj.testInterpolate(5, 0, 8, 4, 9);
        obj.testInterpolate(0, -5, 5, 4, 5);
        obj.testInterpolate(100, 90, 200, 66, 55);
        obj.testInterpolate(5, 5, 6, 66, 55);
    }

    @Test
    void testfloorInterpolate(){
        //0, 2.5, 5, 7.5, 10
        obj.testInterpolate(2.5, 2);
        obj.testInterpolate(5, 1);
        obj.testInterpolate(3, 3);
        obj.testInterpolate(100, 3);
    }

    @Test
    void InizialzeArr() {
        TestClass obj = new TestClass(new double[]{1, 2, 3}, new double[]{-1, -2, 3});
        System.out.println(obj.apply(2.5));
        obj.insert(2, 999);
        System.out.println();
        for (int i = 0; i < obj.getCount(); ++i) {
            System.out.println(obj.getX(i)+ ", "+ obj.getY(i));
        }
    }
}