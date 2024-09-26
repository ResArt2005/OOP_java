package tests;

import functions.AbstractTabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class AbstractTabulatedFunctionTest {
    class MockTabulatedFunction extends AbstractTabulatedFunction{
        private double x0;
        private double x1;
        private double y0;
        private double y1;
        MockTabulatedFunction(){
            x0 = 3;
            x1 = 9;
            y0 = 5;
            y1 = 8;
        }
        @Override
        protected int floorIndexOfX(double x) {
            return 0;
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
            return 8;
        }
        @Override
        public double getY(int index) {
            return 0;
        }
        @Override
        public void setY(int index, double value) {}
        @Override
        public int indexOfX(double x) {
            return 3;
        }

        @Override
        public int indexOfY(double y) {
            return 5;
        }

        @Override
        public double leftBound() {
            return 0;
        }

        @Override
        public double rightBound() {
            return 0;
        }
    }
    @Test
    void TestObj(){
        MockTabulatedFunction obj = new MockTabulatedFunction();
        System.out.println(obj.apply(4));
        System.out.println(obj.apply(9));
        System.out.println(obj.apply(55));
    }
}