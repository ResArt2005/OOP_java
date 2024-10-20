package functions.myOwnFunctionsForEquation;

import functions.MathFunction;

public interface MathDerivativeAndIntegral extends MathFunction {
    double derivative(double x);
    double integral(double x0, double x);
}
