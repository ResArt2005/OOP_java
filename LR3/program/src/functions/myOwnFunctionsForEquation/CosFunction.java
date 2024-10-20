package functions.myOwnFunctionsForEquation;
import java.lang.Math;
public class CosFunction implements MathDerivativeAndIntegral {
    private final double constant;
    public CosFunction(double constant){
        this.constant = constant;
    }
    public CosFunction(){
        this.constant = 1;
    }
    @Override
    public double apply(double x) {
        return constant*Math.cos(x);
    }

    @Override
    public double derivative(double x) {
        return -constant*Math.sin(x);
    }

    @Override
    public double integral(double x0, double x) {
        return constant*(Math.sin(x) - Math.sin(x0));
    }
}