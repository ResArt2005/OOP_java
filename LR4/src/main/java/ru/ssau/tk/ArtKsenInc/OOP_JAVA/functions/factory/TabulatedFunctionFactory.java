package ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.ArrayTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.StrictTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.UnmodifiableTabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);
    default TabulatedFunction createStrict(double[] xValues, double[] yValues){
        return new StrictTabulatedFunction(create(xValues, yValues));
    }
    default TabulatedFunction createUnmodifiable (double[] xValues, double[] yValues){
        return new UnmodifiableTabulatedFunction(create(xValues, yValues));
    }
    default TabulatedFunction createStrictUnmodifiable (double[] xValues, double[] yValues){
        return new StrictTabulatedFunction(new UnmodifiableTabulatedFunction(create(xValues, yValues)));
    }
}
