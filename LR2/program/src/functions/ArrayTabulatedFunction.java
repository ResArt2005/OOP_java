package functions;

import java.util.Arrays;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removeable {
    private double[] xValues;
    private double[] yValues;

    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        count = this.xValues.length;
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        double[] xValues = new double[count];
        double[] yValues = new double[count];
        if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }
        if (xFrom == xTo) {
            for (int i = 0; i < count; ++i) {
                xValues[i] = xFrom;
                yValues[i] = source.apply(xFrom);
            }
        } else {
            xValues[0] = xFrom;
            yValues[0] = source.apply(xFrom);
            xValues[count - 1] = xTo;
            yValues[count - 1] = source.apply(xTo);

            double step = (xTo - xFrom) / (count - 1);
            double temp = step;

            for (int i = 1; i < count - 1; ++i) {
                xValues[i] = temp;
                yValues[i] = source.apply(temp);
                temp += step;
            }
        }
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        this.count = this.xValues.length;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x > xValues[count - 1]) return count - 1;
        if (x < xValues[0]) return 0;
        for (int i = 0; i < count - 1; ++i) {
            if (xValues[i] <= x) {
                if (xValues[i + 1] > x) {
                    return i;
                }
            }
        }
        return count - 1;
    }

    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) return getY(0);
        return interpolate(x, getX(0), getX(1), getY(0), getY(1));
    }

    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) return getY(0);
        int k = count - 1;
        return interpolate(x, getX(k - 1), getX(k), getY(k - 1), getY(k));
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (count == 1) {
            return getY(0);
        }

        if (floorIndex < 0 || floorIndex >= count - 1) {
            return Double.NaN;
        }

        double leftX = getX(floorIndex);
        double leftY = getY(floorIndex);
        double rightX = getX(floorIndex + 1);//Соседняя точка по x
        double rightY = getY(floorIndex + 1);//Соседняя точка по y

        return interpolate(x, leftX, rightX, leftY, rightY);
    }

    @Override
    public double getX(int index) {
        return index >= 0 && index < count ? xValues[index] : Double.NaN;
    }

    @Override
    public double getY(int index) {
        return index >= 0 && index < count ? yValues[index] : Double.NaN;
    }

    @Override
    public void setY(int index, double value) {
        if (index >= 0 && index < count) {
            yValues[index] = value;
        } else {
            System.out.println("Out of range. This index doesn't exist");
        }
    }

    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < count; ++i) {
            if (xValues[i] == x) return i;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        for (int i = 0; i < count; ++i) {
            if (yValues[i] == y) return i;
        }
        return -1;
    }

    @Override
    public double leftBound() {
        return xValues[0];
    }

    @Override
    public double rightBound() {
        return xValues[count - 1];
    }

    @Override
    public void insert(double x, double y) {
        int i = 0;
        while(getX(i) < x && i < count) ++i;
        if(getX(i) == x) setY(i, y);
        else if (i < count){
            double[] xTempFull = new double[count+1];
            double[] yTempFull = new double[count+1];
            System.arraycopy(xValues, 0, xTempFull, 0, i);
            xTempFull[i] = x;
            System.arraycopy(xValues, i, xTempFull, i + 1, count - i);
            xValues = new double[count+1];
            System.arraycopy(xTempFull, 0, xValues, 0, count + 1);

            System.arraycopy(yValues, 0, yTempFull, 0, i);
            yTempFull[i] = y;
            System.arraycopy(yValues, i, yTempFull, i + 1, count - i);
            yValues = new double[count+1];
            System.arraycopy(yTempFull, 0, yValues, 0, count + 1);
            ++count;
        }
        else{
            double[] xTempFull = new double[count+1];
            double[] yTempFull = new double[count+1];
            System.arraycopy(xValues, 0, xTempFull, 0, i);
            xTempFull[i] = x;
            xValues = new double[count+1];
            System.arraycopy(xTempFull, 0, xValues, 0, count + 1);

            System.arraycopy(yValues, 0, yTempFull, 0, i);
            yTempFull[i] = y;
            yValues = new double[count+1];
            System.arraycopy(yTempFull, 0, yValues, 0, count + 1);
            ++count;
        }
    }

    @Override
    public void remove(int index) {
        if(Double.isNaN(getX(index))){
            System.out.println("Index doesn't exist");
            return;
        }
        else if (index == count - 1) { --count; return; }
        double[] xTempFull = new double[count-1];
        double[] yTempFull = new double[count-1];
        System.arraycopy(xValues, 0, xTempFull, 0, index);
        System.arraycopy(xValues, index + 1, xTempFull, index, count - index - 1);
        System.arraycopy(xTempFull, 0, xValues, 0, count - 1);

        System.arraycopy(yValues, 0, yTempFull, 0, index);
        System.arraycopy(yValues, index + 1, yTempFull, index, count - index - 1);
        System.arraycopy(yTempFull, 0, yValues, 0, count - 1);
        --count;
    }
}
