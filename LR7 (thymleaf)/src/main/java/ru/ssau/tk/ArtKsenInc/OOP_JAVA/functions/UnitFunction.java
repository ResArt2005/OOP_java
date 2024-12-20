package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionInfo;
@MathFunctionInfo(name = "Функция единицы", priority = 1)
public class UnitFunction extends ConstantFunction{
    public UnitFunction(){
        super(1);
    }
    @Override
    public double apply(double x){
        return getConstant();
    }
}
