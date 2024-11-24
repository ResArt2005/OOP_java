package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;

public interface MathFunction {
    double apply(double x);
    default CompositeFunction andThen(MathFunction afterFunction){
        return new CompositeFunction(this, afterFunction);
    }
}
