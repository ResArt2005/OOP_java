package functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    private int count;
    protected abstract int floorIndexOfX(double x);
    protected abstract double extrapolateLeft(double x);
    protected abstract double extrapolateRight(double x);
    protected abstract  double interpolate(double x, int floorIndex);
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY){
        return leftY + ((rightY-leftY)/(rightX-leftX))*(x - leftX);
    }
    @Override
    public int getCount(){
        return count;
    }
    @Override
    public double apply(double x) {
    int k = getCount() - 1;
    if (x < getX(0)) {
        return interpolate(x, getX(0), getX(1), getY(0), getY(1));
    }
    if (x > getX(k)) {
        return interpolate(x, getX(k-1), getX(k), getY(k-1), getY(k));
    }
    int index = indexOfX(x);
    if (index != -1) {
        return getY(index);
    }
    index = floorIndexOfX(x);
    return interpolate(x, getX(index), getX(index+1), getY(index), getY(index+1));
}
}
