package functions;
import java.lang.Math;
public class ctg implements StandardFunction{
    private double constant;
    public ctg(double constant){
        this.constant = constant;
    }
    public ctg(){
        this.constant = 1;
    }
    @Override
    public double f(double x) {
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