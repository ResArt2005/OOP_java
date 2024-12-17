import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent.TabulatedIntegration;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.AInDegreeXFunction;

class TabulatedIntegrationTest {

    @Test
    void call() {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(new AInDegreeXFunction(2, 2),0,10,5);
        TabulatedIntegration tabulatedintegration = new TabulatedIntegration(function,10,0);
        Assertions.assertEquals(3655.88, tabulatedintegration.call(), 0.001);
    }
    @Test
    void testTabulatedIntegrationInvalidFunction() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new TabulatedIntegration(null, 0, 1));
    }
}