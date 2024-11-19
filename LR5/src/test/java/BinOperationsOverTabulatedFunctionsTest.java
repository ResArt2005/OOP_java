import ru.ssau.tk.ArtKsenInc.OOP_JAVA.exceptions.InconsistentFunctionsException;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.ArrayTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.ExpFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.SqrFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class BinOperationsOverTabulatedFunctionsTest {
    ArrayTabulatedFunction arrayTabFunc=new ArrayTabulatedFunction(new SqrFunction(), 0, 9, 3);
    LinkedListTabulatedFunction linkedListTabFunc=new LinkedListTabulatedFunction(new ExpFunction(), 0, 9, 3);
    TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory());
    @Test
    void OperationSum(){
        TabulatedFunction resultOpSum = service.sum(arrayTabFunc, linkedListTabFunc);
        double[]yValuesSum = {1, 92.1384, 8106.0839};
        for (int i = 0; i < resultOpSum.getCount(); ++i) {
           Assertions.assertEquals(yValuesSum[i], resultOpSum.getY(i), 0.0001);
        }
    }
    @Test
    void OperationSub(){
        TabulatedFunction resultOpSub = service.subtract(arrayTabFunc, linkedListTabFunc);
        double[]yValuesSub = {-1, -87.8958, -8100.0839};
         for (int i = 0; i < resultOpSub.getCount(); ++i) {
           Assertions.assertEquals(yValuesSub[i], resultOpSub.getY(i), 0.0001);
        }
    }
    @Test
    void OperationMul(){
        TabulatedFunction resultOpMul = service.multiplication(arrayTabFunc, linkedListTabFunc);
        double[]yValuesSum = {0, 190.9551, 24309.2517};
        for (int i = 0; i < resultOpMul.getCount(); ++i) {
           Assertions.assertEquals(yValuesSum[i], resultOpMul.getY(i), 0.0001);
        }
    }
    @Test
    void OperationDev(){
        TabulatedFunction resultOpDev = service.division(arrayTabFunc, linkedListTabFunc);
        double[]yValuesSub = {0, 0.0235, 3.7022941226003863E-4};
         for (int i = 0; i < resultOpDev.getCount(); ++i) {
           Assertions.assertEquals(yValuesSub[i], resultOpDev.getY(i), 0.0001);
         }
    }
    @Test
    void OperationExceptionLengthAreNotTheSame(){
        ArrayTabulatedFunction arrayTabFunc =new ArrayTabulatedFunction(new SqrFunction(), 0, 9, 5);
        LinkedListTabulatedFunction linkedListTabFunc=new LinkedListTabulatedFunction(new ExpFunction(), 0, 9, 3);
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        Assertions.assertThrows(InconsistentFunctionsException.class, () -> service.subtract(arrayTabFunc, linkedListTabFunc));
    }
    @Test
    void OperationExceptionXsAreNotTheSame(){
        ArrayTabulatedFunction arrayTabFunc =new ArrayTabulatedFunction(new SqrFunction(), 0, 90, 3);
        LinkedListTabulatedFunction linkedListTabFunc=new LinkedListTabulatedFunction(new ExpFunction(), 0, 9, 3);
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        Assertions.assertThrows(InconsistentFunctionsException.class, () -> service.subtract(arrayTabFunc, linkedListTabFunc));
        InconsistentFunctionsException exception = Assertions.assertThrows(InconsistentFunctionsException.class, () -> {
            throw new InconsistentFunctionsException("WAIT IT InconsistentFunctionsException");
        });
        Assertions.assertEquals("WAIT IT InconsistentFunctionsException", exception.getMessage());
    }
}
