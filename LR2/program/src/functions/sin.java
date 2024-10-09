package functions;
import java.lang.Math;
public class sin implements StandardFunction{
    private double constant;
    public sin(double constant){
        this.constant = constant;
    }
    public sin(){
        this.constant = 1;
    }
    @Override
    public double f(double x) {
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
