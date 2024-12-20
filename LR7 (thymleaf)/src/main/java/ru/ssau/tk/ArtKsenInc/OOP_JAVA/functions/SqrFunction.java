package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;
import java.lang.Math;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionInfo;

@MathFunctionInfo(name = "Взятие квадратного корня", priority = 1)
public class SqrFunction implements MathFunction{
    @Override
    public double apply(double x) {
        return Math.sqrt(x);
    }
}
