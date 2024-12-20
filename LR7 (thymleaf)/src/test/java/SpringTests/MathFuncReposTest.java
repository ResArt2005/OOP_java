package SpringTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.CosFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.MathFuncRepos;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)

public class MathFuncReposTest {

    @Autowired
    private MathFuncRepos mathFuncRepos;

    @Test
    public void testSaveAndFindMathFunc() {
        String name = "testFunction";
        MathFunction function = new CosFunction();
        MathFunc mathFunc = new MathFunc(name, function);
        mathFuncRepos.save(mathFunc);
        Optional<MathFunc> foundMathFunc = mathFuncRepos.findById(mathFunc.getId());
        assertTrue(foundMathFunc.isPresent());
        assertEquals(name, foundMathFunc.get().getName());
        assertEquals(function.getClass().getSimpleName(), foundMathFunc.get().getFunction().getClass().getSimpleName());
    }

    @Test
    public void testDeleteMathFunc() {
        String name = "testFunction";
        MathFunction function = new MathFunction() {
            @Override
            public double apply(double x) {
                return x * x;
            }
        };
        MathFunc mathFunc = new MathFunc(name, function);
        mathFuncRepos.save(mathFunc);

        mathFuncRepos.deleteById(mathFunc.getId());
        Optional<MathFunc> deletedMathFunc = mathFuncRepos.findById(mathFunc.getId());
        assertFalse(deletedMathFunc.isPresent());
    }
}
