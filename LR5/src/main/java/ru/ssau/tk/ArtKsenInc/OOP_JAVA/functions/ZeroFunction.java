package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionInfo;
@MathFunctionInfo(name = "Функция нуля", priority = 1)
public class ZeroFunction extends ConstantFunction{
    public ZeroFunction(){
        super(0);
    }
    @Override
    public double apply(double x){
        return getConstant();
    }
}
