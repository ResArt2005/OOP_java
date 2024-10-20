package functions.myOwnFunctionsForEquation;
import java.lang.Math;
public class SinFunction implements MathDerivativeAndIntegral {
    private double constant;
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
