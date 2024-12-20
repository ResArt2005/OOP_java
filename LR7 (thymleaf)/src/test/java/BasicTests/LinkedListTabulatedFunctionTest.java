package BasicTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.exceptions.ArrayIsNotSortedException;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.exceptions.DifferentLengthOfArraysException;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.exceptions.InterpolationException;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.SqrFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.Serial;

class LinkedListTabulatedFunctionTest {
    public class TestClass extends LinkedListTabulatedFunction {
        @Serial
        private static final long serialVersionUID = 1L;

        public TestClass(MathFunction source, double xFrom, double xTo, int count) {
            super(source, xFrom, xTo, count);
        }

        public TestClass(double[] xValues, double[] yValues) {
            super(xValues, yValues);
        }

        public void testfloorIndexOfX(double x) {
            Assertions.assertEquals(1, floorIndexOfX(x));
        }

        public void testExtrapolateLeft(double x) {
            Assertions.assertEquals(1.581, extrapolateLeft(x), 0.001);
        }

        public void testExtrapolateRight(double x) {
            Assertions.assertEquals(1.891, extrapolateRight(x), 0.001);
        }

        public void testInterpolate(double x, double leftX, double rightX, double leftY, double rightY) {
           Assertions.assertEquals(-3.166, interpolate(x, leftX, rightX, leftY, rightY), 0.001);
        }
        public void testInterpolate(double x, int floorIndex) {
            obj.interpolate(x, floorIndex);
        }
    }

    private final SqrFunction fun = new SqrFunction();
    private final double StartX = 0;
    private final double EndX = 10;
    private final int count = 5;
    //0, 2.5, 5, 7.5, 10
    private final TestClass obj = new TestClass(fun, EndX, StartX, count);

    @Test
    void getCount() {
         Assertions.assertEquals(5, obj.getCount());
    }

    @Test
    void getX() {
        Assertions.assertEquals(5, obj.getX(2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> obj.getX(1000));
    }

    @Test
    void getY() {
        Assertions.assertEquals(2.236, obj.getY(2), 0.001);
        Assertions.assertThrows(IllegalArgumentException.class, () -> obj.getY(1000));
    }

    @Test
    void setY() {
        obj.setY(3, 3);
        Assertions.assertEquals(3, obj.getY(3));
        Assertions.assertThrows(IllegalArgumentException.class, () -> obj.setY(1000, 8));
    }

    @Test
    void indexOfX() {
        Assertions.assertEquals(1,  obj.indexOfX(2.5));
        Assertions.assertEquals(2, obj.indexOfX(5));
        Assertions.assertEquals(-1, obj.indexOfX(1000));
    }

    @Test
    void indexOfY() {
        Assertions.assertEquals(0, obj.indexOfY(0));
        Assertions.assertEquals(2, obj.indexOfY(2.23606797749979));
        obj.setY(4, 32);
        Assertions.assertEquals(4, obj.indexOfY(32));
        Assertions.assertEquals(-1, obj.indexOfY(1000));
    }

    @Test
    void leftBound() {
        Assertions.assertEquals(0, obj.leftBound());
    }

    @Test
    void rightBound() {
        Assertions.assertEquals(10, obj.rightBound());
    }

    @Test
    void apply() {
        //0, 2.5, 5, 7.5, 10
        Assertions.assertEquals(-1.897, obj.apply(-3), 0.001);
        Assertions.assertEquals(3.501, obj.apply(12), 0.001);
        Assertions.assertEquals(2.236, obj.apply(5), 0.001);
        Assertions.assertEquals(1.712, obj.apply(3), 0.001);
    }

    @Test
    public void testInsert() {
        obj.insert(0, 999);
        Assertions.assertEquals(0, obj.getX(0));
        obj.insert(2, 111);
        Assertions.assertEquals(2, obj.getX(1));
        obj.insert(11, 222);
        Assertions.assertEquals(11, obj.getX( obj.getCount() - 1));
    }

    @Test
    public void testRemove() {
        obj.remove(0);
        Assertions.assertEquals(2.5, obj.leftBound());
        obj.remove(2);
        Assertions.assertEquals(-1, obj.indexOfX(7.5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> obj.remove(1000));
    }

    @Test
    void testfloorIndexOfX() {
        //0, 2.5, 5, 7.5, 10
        obj.testfloorIndexOfX(3);
        Assertions.assertThrows(IllegalArgumentException.class, () -> obj.remove(1000));
    }

    @Test
    public void testExtrapolateLeft() {
        obj.testExtrapolateLeft(2.5);
    }

    @Test
    public void testExtrapolateRight() {
        obj.testExtrapolateRight(2.5);
    }

    @Test
    public void testInterpolate() {
        obj.testInterpolate(2.5, 0, 3, 1, -4);
    }

    @Test
    void InizialzeArr() {
        TestClass obj = new TestClass(new double[]{1, 2, 3}, new double[]{-1, -2, 3});
        Assertions.assertEquals(0.5, obj.apply(2.5));
        obj.insert(2, 999);
        Assertions.assertEquals(2, obj.getX(1));
    }
    @Test
    void ThrowInterpolationException(){
        Assertions.assertThrows(InterpolationException.class, () -> obj.testInterpolate(3, 1000));
                InterpolationException exception = Assertions.assertThrows(InterpolationException.class, () -> {
            throw new InterpolationException("WAIT YOU CANNOT INTERPOLATE");
        });
        Assertions.assertEquals("WAIT YOU CANNOT INTERPOLATE", exception.getMessage());
    }
    @Test
    void ThrowDifferentLengthOfArraysException(){
        Assertions.assertThrows(DifferentLengthOfArraysException.class, () -> new TestClass(new double[]{1, 2, 3}, new double[]{1, 2}));
        DifferentLengthOfArraysException exception = Assertions.assertThrows(DifferentLengthOfArraysException.class, () -> {
            throw new DifferentLengthOfArraysException("WAIT THEY HAVE DIFFERENT LENGTH");
        });
        Assertions.assertEquals("WAIT THEY HAVE DIFFERENT LENGTH", exception.getMessage());
    }
    @Test
    void ThrowArrayIsNotSortedException(){
        Assertions.assertThrows(ArrayIsNotSortedException.class, () -> new TestClass(new double[]{1, 3, 2,}, new double[]{1, 2, 3}));
        ArrayIsNotSortedException exception = Assertions.assertThrows(ArrayIsNotSortedException.class, () -> {
            throw new ArrayIsNotSortedException("WAIT IT DOESN'T SORTED");
        });
        Assertions.assertEquals("WAIT IT DOESN'T SORTED", exception.getMessage());
    }
}