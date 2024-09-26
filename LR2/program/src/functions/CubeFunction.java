package functions;

public class CubeFunction implements MathFunction{
    @Override
    public double apply(double x) {
        return x*x*x;
    }
}
