package ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;

abstract public class SteppingDifferentialOperator implements DifferentialOperator<MathFunction> {
    protected double step;
    public SteppingDifferentialOperator(double step){
        if(Double.isNaN(step) || Double.isInfinite(step) || step <= 0)
            throw new IllegalArgumentException();
        this.step = step;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }
}
