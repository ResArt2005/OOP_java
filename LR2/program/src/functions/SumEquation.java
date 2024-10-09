package functions;

import java.util.Arrays;

public class SumEquation implements StandardFunction{
    StandardFunction[] equation;
    public SumEquation(StandardFunction[] funcs){
        equation = Arrays.copyOf(funcs, funcs.length);
    }
    @Override
    public double f(double x) {
        double sum = 0;
        for (int i = 0; i < equation.length; ++i) {

        }
        return 0;
    }

    @Override
    public double derivative(double x) {
        return 0;
    }

    @Override
    public double integral(double x0, double x) {
        return 0;
    }
}
