package functions.factory;
import functions.ArrayTabulatedFunction;
import functions.StrictTabulatedFunction;
import functions.TabulatedFunction;
import functions.UnmodifiableTabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);
    default TabulatedFunction createStrict(double[] xValues, double[] yValues){
        return new StrictTabulatedFunction(create(xValues, yValues));
    }
}
