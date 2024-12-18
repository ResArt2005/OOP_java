package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.myOwnFunctionsForEquation.MathDerivativeAndIntegral;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionInfo;
@MathFunctionInfo(name = "Тангенс", priority = 3)
public class TgFunction implements MathDerivativeAndIntegral {
    private final double constant;
    public TgFunction(double constant){
        this.constant = constant;
    }
    public TgFunction(){
        this.constant = 1;
    }
    @Override
    public double apply(double x) {
        return constant*Math.tan(x);
    }

    @Override
    public double derivative(double x) {
        return constant/Math.pow(Math.cos(x), 2);
    }

    @Override
    public double integral(double x0, double x) {
        return -constant*(Math.log(Math.abs(Math.cos(x))) - Math.log(Math.abs(Math.cos(x0))));
    }
}