package operations;
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
     TabulatedFunction doOperation(TabulatedFunction a, TabulatedFunction b, BiOperation operation){

        return null;
     }
}
