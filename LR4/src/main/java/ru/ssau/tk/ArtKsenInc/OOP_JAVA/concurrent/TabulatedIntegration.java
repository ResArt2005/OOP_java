package ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import java.util.concurrent.Callable;
public class TabulatedIntegration implements Callable<Double>{
    private final TabulatedFunction function;
    private double start;
    private double end;
    public TabulatedIntegration(TabulatedFunction tabulatedfunction, double x1, double x2){
        if (tabulatedfunction == null){
            throw  new IllegalArgumentException();
        }
        this.function = tabulatedfunction;
        this.start = x1;
        this.end = x2;
        if(start > end){
            double temp = end;
            end = start;
            start = temp;
        }
    }
    private double TrapezoidalIntegration(){
        int countOfIterations = 10000;
        double partOfTrapezoid = (end - start) / countOfIterations;
        double sum = 0;
        for (int i = 0; i < countOfIterations; i++){
            double XPrev = start + (i * partOfTrapezoid);
            double XNext = XPrev + partOfTrapezoid;
            sum += (function.apply(XPrev) + function.apply(XNext)) * partOfTrapezoid / 2;
        }
        return sum;
    }
    @Override
    public Double call(){
        return TrapezoidalIntegration();
    }
}
