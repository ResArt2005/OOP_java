package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.myOwnFunctionsForEquation.MathDerivativeAndIntegral;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionInfo;
@MathFunctionInfo(name = "Синус", priority = 3)
public class SinFunction implements MathDerivativeAndIntegral {
    private final double constant;
    public SinFunction(double constant){
        this.constant = constant;
    }
    public SinFunction(){
        this.constant = 1;
    }
    @Override
    public double apply(double x) {
        return constant*Math.sin(x);
    }

    @Override
    public double derivative(double x) {
        return constant*Math.cos(x);
    }

    @Override
    public double integral(double x0, double x) {
        return -constant*(Math.cos(x) - Math.cos(x0));
    }
}
