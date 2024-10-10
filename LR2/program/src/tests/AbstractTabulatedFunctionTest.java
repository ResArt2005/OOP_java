package tests;

import functions.AbstractTabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractTabulatedFunctionTest {
    class MockTabulatedFunction extends AbstractTabulatedFunction {
        private double x0;
        private double x1;
        private double y0;
        private double y1;

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
    }

    @Test
    void TestObj() {
        MockTabulatedFunction obj = new MockTabulatedFunction();
        Assertions.assertEquals(5, obj.apply(4));
        Assertions.assertEquals(9, obj.apply(11));
        Assertions.assertEquals(3.5, obj.apply(0));
        Assertions.assertEquals(5, obj.apply(8));
    }
}