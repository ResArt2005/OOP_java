package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.myOwnFunctionsForEquation;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;

public interface MathDerivativeAndIntegral extends MathFunction {
    double derivative(double x);
    double integral(double x0, double x);
}
