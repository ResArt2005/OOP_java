package functions;

public class SquareFunction implements MathFunction{
    @Override
    public double apply(double x) {
        return x*x;
    }
}
