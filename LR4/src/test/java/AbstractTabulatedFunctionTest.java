import ru.ssau.tk.ArtKsenInc.OOP_JAVA.exceptions.ArrayIsNotSortedException;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.exceptions.DifferentLengthOfArraysException;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.AbstractTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.Serial;
import java.util.Iterator;

class AbstractTabulatedFunctionTest {
    private final MockTabulatedFunction mockTabulatedFunction = new MockTabulatedFunction();

    static class MockTabulatedFunction extends AbstractTabulatedFunction {
        @Serial
        private static final long serialVersionUID = 1L;
        private final double x0;
        private final double x1;
        private final double y0;
        private final double y1;

        MockTabulatedFunction() {
            x0 = 3;
            x1 = 9;
            y0 = 5;
            y1 = 8;
        }

        @Override
        protected int floorIndexOfX(double x) {
            if(x0 > x) return 0;
            if(x1 < x) return count;
            if(x0 < x) return 0;
            else return 1;
        }

        @Override
        protected double extrapolateLeft(double x) {
            return 0;
        }

        @Override
        protected double extrapolateRight(double x) {
            return 0;
        }

        @Override
        protected double interpolate(double x, int floorIndex) {
            return 0;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public double getX(int index) {
            return index == 0 ? x0 : x1;
        }

        @Override
        public double getY(int index) {
            return index == 0 ? y0 : y1;
        }

        @Override
        public void setY(int index, double value) {
        }

        @Override
        public int indexOfX(double x) {
            return 0;
        }

        @Override
        public int indexOfY(double y) {
            return 0;
        }

        @Override
        public double leftBound() {
            return 0;
        }

        @Override
        public double rightBound() {
            return 0;
        }
        @Override
        public double apply(double x){

        int k = getCount() - 1;
        if (x < getX(0)) {
            return interpolate(x, getX(0), getX(1), getY(0), getY(1));
        }
        if (x > getX(k)) {
            return interpolate(x, getX(k - 1), getX(k), getY(k - 1), getY(k));
        }
        int index = indexOfX(x);
        if (index != -1) {
            return getY(index);
        }
        index = floorIndexOfX(x);
        return interpolate(x, getX(index), getX(index + 1), getY(index), getY(index + 1));
        }

        public void TestThrowDifferentLengthOfArraysException(double[]xValues, double[] yValues){
            Assertions.assertThrows(DifferentLengthOfArraysException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues);
            }
        });
        }
        public void TestThrowArrayIsNotSortedException(double[]xValues){
            Assertions.assertThrows(ArrayIsNotSortedException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                AbstractTabulatedFunction.checkSorted(xValues);
            }
        });
        }

        @Override
        public Iterator<Point> iterator() {
            return null;
        }
    }

    MockTabulatedFunction obj = mockTabulatedFunction;
    @Test
    void TestMockTabulatedFunction() {
        Assertions.assertEquals(5, obj.apply(4));
        Assertions.assertEquals(9, obj.apply(11));
        Assertions.assertEquals(3.5, obj.apply(0));
        Assertions.assertEquals(5, obj.apply(8));
    }

    @Test
    void TestThrowDifferentLengthOfArraysException(){
        double[] xValues = {1,2,3,4};
        double[] yValues = {1,2,3};
        obj.TestThrowDifferentLengthOfArraysException(xValues, yValues);
    }

    @Test
    void TestThrowArrayIsNotSortedException(){
        double[] xValues1 = {1,4,2,3};
        double[] xValues2 = {4,1,2,3};
        double[] xValues3 = {1,4,3,2};
        obj.TestThrowArrayIsNotSortedException(xValues1);
        obj.TestThrowArrayIsNotSortedException(xValues2);
        obj.TestThrowArrayIsNotSortedException(xValues3);
    }
}