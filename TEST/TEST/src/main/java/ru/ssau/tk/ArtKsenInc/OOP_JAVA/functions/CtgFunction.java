package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.myOwnFunctionsForEquation.MathDerivativeAndIntegral;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionInfo;
@MathFunctionInfo(name = "Котангенс", priority = 3)
public class CtgFunction implements MathDerivativeAndIntegral {
    private final double constant;
    public CtgFunction(double constant){
        this.constant = constant;
    }
    public CtgFunction(){
        this.constant = 1;
    }
    @Override
    public double apply(double x) {
        return constant*Math.tan(x);
    }

    @Override
    public double derivative(double x) {
        return -constant/Math.pow(Math.sin(x), 2);
    }

    @Override
    public double integral(double x0, double x) {
        return constant*(Math.log(Math.abs(Math.sin(x))) - Math.log(Math.abs(Math.sin(x0))));
    }
}