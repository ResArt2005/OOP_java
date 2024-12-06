package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompositeFunction implements MathFunction{
    private final MathFunction firstFunction ;
    private final MathFunction secondFunction;
    public CompositeFunction(MathFunction g, MathFunction f){
        firstFunction = g;
        secondFunction = f;
    }
    @Override
    public double apply(double x){
        return firstFunction.apply(secondFunction.apply(x));
    }
}
