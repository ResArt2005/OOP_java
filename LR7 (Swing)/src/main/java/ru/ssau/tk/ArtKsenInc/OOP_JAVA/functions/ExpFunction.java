package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.myOwnFunctionsForEquation.MathDerivativeAndIntegral;

import java.lang.Math;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionInfo;

@MathFunctionInfo(name = "Экспонента", priority = 1)
public class ExpFunction implements MathDerivativeAndIntegral {
    private final double constant;
    private double degree;

    public ExpFunction(double constant) {
        this.constant = constant;
    }

    public ExpFunction(double constant, double degree) {
        this.constant = constant;
        this.degree = degree;
    }

    public ExpFunction() {
        this.constant = 1;
        this.degree = 1;
    }

    @Override
    public double apply(double x) {
        return constant * Math.exp(x * degree);
    }

    @Override
    public double derivative(double x) {
        return constant * degree * Math.exp(x * degree);
    }

    @Override
    public double integral(double x0, double x) {
        return constant * (Math.exp(x * degree) / degree) - constant * (Math.exp(x0 * degree) / degree);
    }
}
