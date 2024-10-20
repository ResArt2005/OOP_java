package functions.myOwnFunctionsForEquation;
import java.lang.Math;
public class ExpFunction implements MathDerivativeAndIntegral {
    private final double constant;
    private double degree;
    public ExpFunction(double constant){
        this.constant = constant;
    }
    public ExpFunction(double constant, double degree){
        this.constant = constant;
        this.degree = degree;
    }
    public ExpFunction(){
        this.constant = 1;
        this.degree = 1;
    }
    @Override
    public double apply(double x) {
        return constant*Math.exp(x*degree);
    }

    @Override
    public double derivative(double x) {
        return constant*degree*Math.exp(x*degree);
    }

    @Override
    public double integral(double x0, double x) {
        return constant*(Math.exp(x*degree)/degree) - constant*(Math.exp(x0*degree)/degree);
    }
}
