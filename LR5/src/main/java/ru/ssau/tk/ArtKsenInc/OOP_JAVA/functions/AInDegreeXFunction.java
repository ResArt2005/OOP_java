package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.myOwnFunctionsForEquation.MathDerivativeAndIntegral;

import java.lang.Math;
public class AInDegreeXFunction implements MathDerivativeAndIntegral {
    private final double constant;
    private final double a;
    public AInDegreeXFunction(double constant, double a){
        this.constant = constant;
        this.a = a;
    }
    public AInDegreeXFunction(double a){
        this.constant = 1;
        this.a = a;
    }
    @Override
    public double apply(double x) {
        return constant*Math.pow(a, x);
    }

    @Override
    public double derivative(double x) {
        return constant*Math.pow(a, x)*Math.log(a);
    }

    @Override
    public double integral(double x0, double x) {
        return constant*(Math.pow(a, x) - Math.pow(a, x0))/Math.log(a);
    }
}
