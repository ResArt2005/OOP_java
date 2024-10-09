package functions;
import java.lang.Math;
public class cos implements StandardFunction{
    private double constant;
    public cos(double constant){
        this.constant = constant;
    }
    public cos(){
        this.constant = 1;
    }
    @Override
    public double f(double x) {
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