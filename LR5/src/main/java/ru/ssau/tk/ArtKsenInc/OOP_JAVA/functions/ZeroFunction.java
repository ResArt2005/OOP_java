package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;

public class ZeroFunction extends ConstantFunction{
    public ZeroFunction(){
        super(0);
    }
    @Override
    public double apply(double x){
        return getConstant();
    }
}
