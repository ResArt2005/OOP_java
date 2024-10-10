package functions;
import java.lang.Math;
public class tg implements StandardFunction{
    private double constant;
    public tg(double constant){
        this.constant = constant;
    }
    public tg(){
        this.constant = 1;
    }
    @Override
    public double f(double x) {
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