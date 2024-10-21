package operations;

import functions.MathFunction;

public class LeftSteppingDifferentialOperator extends SteppingDifferentialOperator {
    public LeftSteppingDifferentialOperator(double step){
        super(step);
    }
    @Override
    public MathFunction derive(MathFunction function) {
        return new MathFunction(){
            @Override
            public double apply(double x) {
                return (function.apply(x) - function.apply(x - step))/step;
            }
        };
    }
}
