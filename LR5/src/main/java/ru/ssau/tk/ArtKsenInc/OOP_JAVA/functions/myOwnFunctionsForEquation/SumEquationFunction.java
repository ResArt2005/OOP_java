package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.myOwnFunctionsForEquation;

import java.util.Arrays;

public class SumEquationFunction implements MathDerivativeAndIntegral {
    MathDerivativeAndIntegral[] equation;
    public SumEquationFunction(MathDerivativeAndIntegral[] func){
        equation = Arrays.copyOf(func, func.length);
    }
    @Override
    public double apply(double x) {
        double sum = 0;
        for (int i = 0; i < equation.length; ++i) {
            sum += equation[i].apply(x);
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
