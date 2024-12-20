package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionInfo;
@MathFunctionInfo(name = "Тождественная функция", priority = 1)
public class IdentityFunction implements MathFunction{
    @Override
    public double apply(double x) {
        return x;
    }
}
