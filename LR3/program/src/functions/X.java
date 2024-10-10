package functions;
import java.lang.Math;
public class X implements StandardFunction{
    private double constant;
    private double degree;
    public X(double constant){
        this.constant = constant;
    }
    public X(double constant, double degree){
        this.constant = constant;
        this.degree = degree;
    }
    public X(){
        this.constant = 1;
        this.degree = 1;
    }
    @Override
    public double f(double x) {
        return constant*Math.pow(x, degree);
    }

    @Override
    public double derivative(double x) {
        return constant*degree*Math.pow(x, degree-1);
    }

    @Override
    public double integral(double x0, double x) {
        if(degree!= 1)
            return constant*(Math.pow(x, degree+1)/(degree+1)) - constant*(Math.pow(x0, degree+1)/(degree+1));
        else return constant*(Math.log(x) - Math.log(x0));
    }
}
