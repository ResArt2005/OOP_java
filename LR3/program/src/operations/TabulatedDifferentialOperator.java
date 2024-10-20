package operations;

import functions.Point;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction>{
    TabulatedFunctionFactory factory;
    public TabulatedDifferentialOperator(TabulatedFunctionFactory factory){
        this.factory = factory;
    }
    public TabulatedDifferentialOperator(){
        this.factory = new ArrayTabulatedFunctionFactory();
    }
    public TabulatedFunctionFactory getFactory(){
        return factory;
    }
    public void setFactory(TabulatedFunctionFactory factory){
        this.factory = factory;
    }
    @Override
    public TabulatedFunction derive(TabulatedFunction function) {
        Point[] points = TabulatedFunctionOperationService.asPoints(function);
        int length = points.length;
        double[] xValues = new double[length];
        double[] yValues = new double[length];
        // первые n-1, вычисленные по формуле правой производной
        for (int i = 0; i < length - 1; ++i) {
            xValues[i] = points[i].x;
            yValues[i] = (points[i + 1].y - points[i].y) / (points[i + 1].x - points[i].x);
        }
        // Последнее значение, вычисленное по формуле левой производной
        xValues[length - 1] = points[length - 1].x;
        yValues[length - 1] = (points[length - 1].y - points[length - 2].y) / (points[length - 1].x - points[length - 2].x);
        return factory.create(xValues, yValues);
    }
}
