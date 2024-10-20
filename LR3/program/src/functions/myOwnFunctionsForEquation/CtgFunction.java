package functions.myOwnFunctionsForEquation;
import java.lang.Math;
public class CtgFunction implements MathDerivativeAndIntegral {
    private double constant;
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