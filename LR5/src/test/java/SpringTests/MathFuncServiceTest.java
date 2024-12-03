package SpringTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.MathFuncRepos;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.web.service.MathFuncService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
public class MathFuncServiceTest {

    @Autowired
    private MathFuncService mathFuncService;

    @Autowired
    private MathFuncRepos mathFuncRepository;

    @BeforeEach
    public void setUp() {
        mathFuncRepository.deleteAll();
    }

    @Test
    public void testCreate() {
        MathFunc mathFunc = new MathFunc(1.0, 2.0, 12345L);
        MathFunc createdMathFunc = mathFuncService.create(mathFunc);
        assertNotNull(createdMathFunc.getId());
        assertEquals(1.0, createdMathFunc.getX());
        assertEquals(2.0, createdMathFunc.getY());
        assertEquals(12345L, createdMathFunc.getHash());
    }

    @Test
    public void testReadAll() {
        MathFunc mathFunc1 = new MathFunc(1.0, 2.0, 12345L);
        MathFunc mathFunc2 = new MathFunc(3.0, 4.0, 67890L);
        mathFuncService.create(mathFunc1);
        mathFuncService.create(mathFunc2);

        List<MathFunc> mathFuncs = mathFuncService.readAll();
        assertEquals(2, mathFuncs.size());
    }

    @Test
    public void testFindByHash() {
        MathFunc mathFunc1 = new MathFunc(1.0, 2.0, 12345L);
        MathFunc mathFunc2 = new MathFunc(3.0, 4.0, 12345L);
        mathFuncService.create(mathFunc1);
        mathFuncService.create(mathFunc2);

        List<MathFunc> foundMathFuncs = mathFuncService.findByHash(12345L);
        assertEquals(2, foundMathFuncs.size());
    }

    @Test
    public void testFindByXAndHash() {
        MathFunc mathFunc = new MathFunc(1.0, 2.0, 12345L);
        MathFunc createdMathFunc = mathFuncService.create(mathFunc);

        MathFunc foundMathFunc = mathFuncService.findByXAndHash(1.0, 12345L);
        assertNotNull(foundMathFunc);
        assertEquals(1.0, foundMathFunc.getX());
        assertEquals(2.0, foundMathFunc.getY());
        assertEquals(12345L, foundMathFunc.getHash());
    }

    @Test
    public void testUpdate() {
        MathFunc mathFunc = new MathFunc(1.0, 2.0, 12345L);
        MathFunc createdMathFunc = mathFuncService.create(mathFunc);

        createdMathFunc.setX(3.0);
        createdMathFunc.setY(4.0);
        MathFunc updatedMathFunc = mathFuncService.update(createdMathFunc);
        assertEquals(3.0, updatedMathFunc.getX());
        assertEquals(4.0, updatedMathFunc.getY());
    }

    @Test
    public void testDeleteById() {
        MathFunc mathFunc = new MathFunc(1.0, 2.0, 12345L);
        MathFunc createdMathFunc = mathFuncService.create(mathFunc);

        mathFuncService.deleteById(createdMathFunc.getId());
        List<MathFunc> mathFuncs = mathFuncService.readAll();
        assertTrue(mathFuncs.isEmpty());
    }

    @Test
    public void testDeleteByHash() {
        MathFunc mathFunc1 = new MathFunc(1.0, 2.0, 12345L);
        MathFunc mathFunc2 = new MathFunc(3.0, 4.0, 12345L);
        mathFuncService.create(mathFunc1);
        mathFuncService.create(mathFunc2);

        mathFuncService.deleteByHash(12345L);
        List<MathFunc> mathFuncs = mathFuncService.readAll();
        assertTrue(mathFuncs.isEmpty());
    }

    @Test
    public void testDeleteByXAndHash() {
        MathFunc mathFunc = new MathFunc(1.0, 2.0, 12345L);
        MathFunc createdMathFunc = mathFuncService.create(mathFunc);

        mathFuncService.deleteByXAndHash(1.0, 12345L);
        List<MathFunc> mathFuncs = mathFuncService.readAll();
        assertTrue(mathFuncs.isEmpty());
    }

    @Test
    public void testDeleteAll() {
        MathFunc mathFunc1 = new MathFunc(1.0, 2.0, 12345L);
        MathFunc mathFunc2 = new MathFunc(3.0, 4.0, 67890L);
        mathFuncService.create(mathFunc1);
        mathFuncService.create(mathFunc2);

        mathFuncService.deleteAll();
        List<MathFunc> mathFuncs = mathFuncService.readAll();
        assertTrue(mathFuncs.isEmpty());
    }
}
