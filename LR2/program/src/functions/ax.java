package functions;
import java.lang.Math;
public class ax implements StandardFunction{
    private double constant;
    private double a;
    public ax(double constant, double a){
        this.constant = constant;
    }
    public ax(double a){
        this.constant = 1;
        this.a = a;
    }
    @Override
    public double f(double x) {
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
