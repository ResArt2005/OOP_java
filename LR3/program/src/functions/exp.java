package functions;
import java.lang.Math;
public class exp implements StandardFunction{
    private double constant;
    private double degree;
    public exp(double constant){
        this.constant = constant;
    }
    public exp(double constant, double degree){
        this.constant = constant;
        this.degree = degree;
    }
    public exp(){
        this.constant = 1;
        this.degree = 1;
    }
    @Override
    public double f(double x) {
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
