import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.SqrFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.StrictTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.UnmodifiableTabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class UnmodifiablePlusStrictTabulatedTest {
    StrictTabulatedFunction obj = new StrictTabulatedFunction(new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(new SqrFunction(), 0, 10, 5)));
    @Test
    void testApplyChanges(){
        Assertions.assertThrows(UnsupportedOperationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                obj.setY(3, 999);
            }
        });
    }
    @Test
    void testTryInterpolate(){
        Assertions.assertThrows(UnsupportedOperationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                obj.apply(15);
            }
        });
    }
}
