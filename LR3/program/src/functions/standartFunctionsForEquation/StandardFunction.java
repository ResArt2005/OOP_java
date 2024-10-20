package functions.standartFunctionsForEquation;

import functions.MathFunction;

public interface StandardFunction extends MathFunction {
    double derivative(double x);
    double integral(double x0, double x);
}
