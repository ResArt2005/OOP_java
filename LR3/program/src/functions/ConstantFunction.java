package functions;

public class ConstantFunction implements MathFunction{
    private double constant;
    public ConstantFunction(double number){
        constant = number;
    }
    public double getConstant(){
        return constant;
    }
    @Override
    public double apply(double x){
        return constant;
    }
}
