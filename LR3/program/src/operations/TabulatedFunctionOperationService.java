package operations;
import exceptions.InconsistentFunctionsException;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import functions.Point;
import functions.TabulatedFunction;

public class TabulatedFunctionOperationService {
    private TabulatedFunctionFactory factory;
    public TabulatedFunctionOperationService(TabulatedFunctionFactory factory){
        this.factory = factory;
    }
    public TabulatedFunctionOperationService(){
        this.factory = new ArrayTabulatedFunctionFactory();
    }
    private interface BiOperation{
        double apply(double u, double v);
    }
    public TabulatedFunctionFactory getFactory(){
        return factory;
    }
    public void setFactory(TabulatedFunctionFactory factory){
        this.factory = factory;
    }
    public static Point[] asPoints(TabulatedFunction tabulatedFunction){
        Point[] points = new Point[tabulatedFunction.getCount()];
        int i = 0;
        for(Point point : tabulatedFunction){
            points[i] = point;
            ++i;
        }
        return points;
    }
     private TabulatedFunction doOperation(TabulatedFunction a, TabulatedFunction b, BiOperation operation){
     if (a.getCount() != b.getCount()) {
            throw new InconsistentFunctionsException();
        }
        int length = a.getCount();
        Point[] pointsA = asPoints(a);
        Point[] pointsB = asPoints(b);

        double[] xValues = new double[length];
        double[] yValues = new double[length];

        for (int i = 0; i < length; i++) {
            xValues[i] = pointsA[i].x; // Получаем x из первой функции
            if (xValues[i] != pointsB[i].x) {
                throw new InconsistentFunctionsException();
            }
            yValues[i] = operation.apply(pointsA[i].y, pointsB[i].y); // Применяем операцию к y
        }

        return factory.create(xValues, yValues);
    }

    public TabulatedFunction sum(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (u, v) -> u + v); // Сложение
    }

    public TabulatedFunction subtract(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (u, v) -> u - v); // Вычитание
    }

    public TabulatedFunction multiplication(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (u, v) -> u * v); // Сложение
    }

    public TabulatedFunction division(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (u, v) -> u / v); // Вычитание
    }
}
