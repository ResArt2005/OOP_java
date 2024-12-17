package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionInfo;
@MathFunctionInfo(name = "Функция возведения в квадрат", priority = 1)
public class SquareFunction implements MathFunction{
    @Override
    public double apply(double x) {
        return x*x;
    }
}
