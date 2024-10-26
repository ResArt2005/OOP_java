import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.ArrayTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.SqrFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.myOwnFunctionsForEquation.AInDegreeXFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedDifferentialOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TabulatedDifferentialOperatorTest {
    TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();
    ArrayTabulatedFunction arrayTabFunc = new ArrayTabulatedFunction(new SqrFunction(), 0, 10, 5);
    LinkedListTabulatedFunction linkedListTabFunc = new LinkedListTabulatedFunction(new AInDegreeXFunction(3, 5), -3, 3, 7);
    @Test
    void deriveTestArrayFactory() {
        TabulatedFunction NewArrayTabFunc = operator.derive(arrayTabFunc);
        double[] xValues = {0, 2.5, 5, 7.5, 10};
        double[] yValues = {0.6324, 0.2619, 0.2010,  0.1694, 0.1694};
        for (int i = 0; i < NewArrayTabFunc.getCount(); ++i) {
            Assertions.assertEquals(xValues[i], NewArrayTabFunc.getX(i));
            Assertions.assertEquals(yValues[i], NewArrayTabFunc.getY(i), 0.0001);
        }
    }
    @Test
    void deriveTestLinkedListFactory() {
        operator.setFactory(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction NewLinkedListTabFunc = operator.derive(linkedListTabFunc);
        double[] xValues = {-3,-2,-1,0,1,2,3};
        double[] yValues = {0.096, 0.48, 2.4, 12, 60, 300, 300};
        for (int i = 0; i < NewLinkedListTabFunc.getCount(); ++i) {
            Assertions.assertEquals(xValues[i], NewLinkedListTabFunc.getX(i));
            Assertions.assertEquals(yValues[i], NewLinkedListTabFunc.getY(i), 0.001);
        }
    }
}