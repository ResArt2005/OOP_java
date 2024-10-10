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
            sum += equation[i].f(x);
        }
        return sum;
    }
    //
    @Override
    public double derivative(double x) {
        double sum = 0;
        for (int i = 0; i < equation.length; ++i) {
            sum += equation[i].derivative(x);
        }
        return sum;
    }

    @Override
    public double integral(double x0, double x) {
        double sum = 0;
        for (int i = 0; i < equation.length; ++i) {
            sum += equation[i].integral(x0, x);
        }
        return sum;
    }
}
