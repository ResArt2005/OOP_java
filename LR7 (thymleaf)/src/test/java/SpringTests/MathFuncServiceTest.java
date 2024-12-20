package SpringTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.CosFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.ExpFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.SinFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.MathFuncRepos;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service.MathFuncService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
class MathFuncServiceTest {

    @Autowired
    private MathFuncService MathFuncService;

    @Autowired
    private MathFuncRepos MathFuncRepository;

    @BeforeEach
    public void setUp() {
        MathFuncRepository.deleteAll();
    }

    @Test
    void create() {
        MathFunc mathFunc = new MathFunc("Test", new CosFunction().andThen(new SinFunction().andThen(new ExpFunction())));
        MathFunc createdMathFunc = MathFuncService.create(mathFunc);
        assertNotNull(createdMathFunc.getId());
        assertEquals("Test", createdMathFunc.getName());
        assertEquals(new CosFunction().andThen(new SinFunction().andThen(new ExpFunction())).apply(45), createdMathFunc.getFunction().apply(45));
    }

    @Test
    void readAll() {
        MathFunc mathFunc1 = new MathFunc("test1", new CosFunction().andThen(new SinFunction()));
        MathFunc mathFunc2 = new MathFunc("test2", new SinFunction().andThen(new CosFunction()));
        MathFuncService.create(mathFunc1);
        MathFuncService.create(mathFunc2);

        Map<Integer, MathFunc> mathFuncs = MathFuncService.readAll();
        assertEquals(2, mathFuncs.size());
    }

    @Test
    void update() {
        MathFunc mathFunc = new MathFunc("test1", new CosFunction().andThen(new SinFunction()));
        int id = MathFuncService.create(mathFunc).getId();
        MathFunc updatedMathFunc = MathFuncService.update(id, new MathFunc("Test2", new SinFunction().andThen(new CosFunction())));
        assertEquals("Test2", updatedMathFunc.getName());
        assertEquals(new SinFunction().andThen(new CosFunction()).apply(45), updatedMathFunc.getFunction().apply(45));
    }

    @Test
    void deleteById() {
        MathFunc MathFunc = new MathFunc("Test", new CosFunction().andThen(new SinFunction()));
        MathFunc createdMathFunc = MathFuncService.create(MathFunc);

        MathFuncService.deleteById(createdMathFunc.getId());
        Map<Integer, MathFunc> MathFuncs = MathFuncService.readAll();
        assertTrue(MathFuncs.isEmpty());
    }

    @Test
    void deleteAll() {
        MathFunc mathFunc1 = new MathFunc("test1", new CosFunction().andThen(new SinFunction()));
        MathFunc mathFunc2 = new MathFunc("test2", new SinFunction().andThen(new CosFunction()));
        MathFuncService.create(mathFunc1);
        MathFuncService.create(mathFunc2);

        MathFuncService.deleteAll();
        Map<Integer, MathFunc> mathFuncs = MathFuncService.readAll();
        assertTrue(mathFuncs.isEmpty());
    }
}