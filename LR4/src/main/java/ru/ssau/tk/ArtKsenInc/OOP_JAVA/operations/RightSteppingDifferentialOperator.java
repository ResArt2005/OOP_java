package ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;

public class RightSteppingDifferentialOperator extends SteppingDifferentialOperator {
    public RightSteppingDifferentialOperator(double step){
        super(step);
    }
    @Override
    public MathFunction derive(MathFunction function) {
        return new MathFunction(){
            @Override
            public double apply(double x) {
                return (function.apply(x + step) - function.apply(x))/step;
            }
        };
    }
}