package functions;

public class ZeroFunction extends ConstantFunction{
    public ZeroFunction(){
        super(0);
    }
    @Override
    public double apply(double x){
        return getConstant();
    }
}
