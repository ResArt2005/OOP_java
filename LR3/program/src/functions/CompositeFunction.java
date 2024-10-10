package functions;

public class CompositeFunction implements MathFunction{
    private MathFunction firstFunction ;
    private MathFunction secondFunction;
    public CompositeFunction(MathFunction g, MathFunction f){
        firstFunction = g;
        secondFunction = f;
    }
    @Override
    public double apply(double x){
        return firstFunction.apply(secondFunction.apply(x));
    }
}
