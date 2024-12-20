package BasicTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent.TabulatedIntegrationExecutor;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.AInDegreeXFunction;

class TabulatedIntegrationExecutorTest {
    LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(new AInDegreeXFunction(2, 2),0,10,5);
    @Test
    void testIntegrationWithValidFunction() throws Exception {
        TabulatedIntegrationExecutor executor = new TabulatedIntegrationExecutor(1000);
        Assertions.assertEquals(3655.88, executor.Integrate(function), 0.001);
        executor.shutdown();
    }

    @Test
    void testIntegrationWithZeroStreams() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new TabulatedIntegrationExecutor(0));
    }

    @Test
    void testIntegrationHandlesNegativeBounds() throws Exception {
        TabulatedIntegrationExecutor executor = new TabulatedIntegrationExecutor(4);
        Assertions.assertEquals(3655.88, executor.Integrate(function), 0.001);
        executor.shutdown();
    }
}