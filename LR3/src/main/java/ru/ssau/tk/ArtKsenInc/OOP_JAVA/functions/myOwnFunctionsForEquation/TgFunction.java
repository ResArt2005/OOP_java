package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.myOwnFunctionsForEquation;
import java.lang.Math;
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