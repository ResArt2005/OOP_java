package SpringTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.TBFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.TBFuncRepos;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service.TBFuncService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)

class TBFuncServiceTest {

    @Autowired
    private TBFuncService tbFuncService;

    @Autowired
    private TBFuncRepos TBFuncRepository;

    @BeforeEach
    public void setUp() {
        TBFuncRepository.deleteAll();
    }

    @Test
    void create() {
        TBFunc mathFunc = new TBFunc(new double[]{1, 2, 3}, new double[]{1, 4, 9});
        TBFunc createdMathFunc = tbFuncService.create(mathFunc);
        assertNotNull(createdMathFunc.getId());
        assertArrayEquals(new double[]{1, 2, 3}, createdMathFunc.getXValues());
        assertArrayEquals(new double[]{1, 4, 9}, createdMathFunc.getYValues());
    }

    @Test
    void readAll() {
        TBFunc mathFunc1 = new TBFunc(new double[]{1, 2, 3}, new double[]{1, 4, 9});
        TBFunc mathFunc2 = new TBFunc(new double[]{1, 4, 9}, new double[]{1, 2, 3});
        tbFuncService.create(mathFunc1);
        tbFuncService.create(mathFunc2);

        Map<Integer, TBFunc> mathFuncs = tbFuncService.readAll();
        assertEquals(2, mathFuncs.size());
    }

    @Test
    void update() {
        TBFunc mathFunc = new TBFunc(new double[]{1, 2, 3}, new double[]{1, 4, 9});
        int id = tbFuncService.create(mathFunc).getId();
        TBFunc updatedTBFunc = tbFuncService.update(id, new TBFunc(new double[]{1, 111, 3}, new double[]{1, 444, 9}));
        assertArrayEquals(new double[]{1, 111, 3}, updatedTBFunc.getXValues());
        assertArrayEquals(new double[]{1, 444, 9}, updatedTBFunc.getYValues());
    }

    @Test
    void deleteById() {
        TBFunc TBfunc = new TBFunc(new double[]{1, 111, 3}, new double[]{1, 4, 9});
        TBFunc createdMathFunc = tbFuncService.create(TBfunc);

        tbFuncService.deleteById(createdMathFunc.getId());
        Map<Integer, TBFunc> TBFuncs = tbFuncService.readAll();
        assertTrue(TBFuncs.isEmpty());
    }

    @Test
    void deleteAll() {
        TBFunc TBFunc1 = new TBFunc(new double[]{1, 2, 3}, new double[]{1, 4, 9});
        TBFunc TBFunc2 = new TBFunc(new double[]{1, 4, 9}, new double[]{1, 2, 3});
        tbFuncService.create(TBFunc1);
        tbFuncService.create(TBFunc2);

        tbFuncService.deleteAll();
        Map<Integer, TBFunc> mathFuncs = tbFuncService.readAll();
        assertTrue(mathFuncs.isEmpty());
    }
}