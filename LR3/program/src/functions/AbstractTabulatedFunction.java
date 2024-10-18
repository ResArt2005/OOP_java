package functions;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    protected int count;

    protected abstract int floorIndexOfX(double x);

    protected abstract double extrapolateLeft(double x);

    protected abstract double extrapolateRight(double x);

    protected abstract double interpolate(double x, int floorIndex);

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + ((rightY - leftY) / (rightX - leftX)) * (x - leftX);
    }

    protected static void checkLengthIsTheSame(double[] xValues, double[] yValues){
        if(xValues.length != yValues.length)
            throw new DifferentLengthOfArraysException();
    }

    protected static void checkSorted(double[] xValues) {
        for (int i = 0; i < xValues.length - 1; ++i) {
            if(xValues[i] > xValues[i+1])
                throw new ArrayIsNotSortedException();
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double apply(double x) {
        //Если x меньше левой границы
        if (x < getX(0)) {
            return extrapolateLeft(x);
        }
        //Если x больше правой границы
        if (x > getX(getCount() - 1)) {
            return extrapolateRight(x);
        }
        //Смотрим, есть ли x в таблице
        int index = indexOfX(x);
        if (index != -1) {
            return getY(index);
        }
        //Если нет, ищем максимальный x из таблицы, который меньше указанного x
        index = floorIndexOfX(x);
        return interpolate(x, index);
    }
}
