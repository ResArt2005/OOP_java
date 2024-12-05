package SpringTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.MathPointRepos;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service.MathPointService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
public class MathPointServiceTest {

    @Autowired
    private MathPointService mathPointService;

    @Autowired
    private MathPointRepos mathFuncRepository;

    @BeforeEach
    public void setUp() {
        mathFuncRepository.deleteAll();
    }

    @Test
    public void testCreate() {
        ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint mathPoint = new ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint(1.0, 2.0);
        ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint createdMathPoint = mathPointService.create(mathPoint);
        assertNotNull(createdMathPoint.getId());
        assertEquals(1.0, createdMathPoint.getX());
        assertEquals(2.0, createdMathPoint.getY());
    }

    @Test
    public void testReadAll() {
        ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint mathPoint1 = new ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint(1.0, 2.0);
        ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint mathPoint2 = new ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint(3.0, 4.0);
        mathPointService.create(mathPoint1);
        mathPointService.create(mathPoint2);

        Map<Integer, ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint> mathFuncs = mathPointService.readAll();
        assertEquals(2, mathFuncs.size());
    }


    @Test
    public void testUpdate() {
        ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint mathPoint = new ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint(1.0, 2.0);
        ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint createdMathPoint = mathPointService.create(mathPoint);

        createdMathPoint.setX(3.0);
        createdMathPoint.setY(4.0);
        ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint updatedMathPoint = mathPointService.update(createdMathPoint);
        assertEquals(3.0, updatedMathPoint.getX());
        assertEquals(4.0, updatedMathPoint.getY());
    }

    @Test
    public void testDeleteById() {
        ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint mathPoint = new ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint(1.0, 2.0);
        ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint createdMathPoint = mathPointService.create(mathPoint);

        mathPointService.deleteById(createdMathPoint.getId());
        Map<Integer, ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint> mathFuncs = mathPointService.readAll();
        assertTrue(mathFuncs.isEmpty());
    }


    @Test
    public void testDeleteAll() {
        ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint mathPoint1 = new ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint(1.0, 2.0);
        ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint mathPoint2 = new ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint(3.0, 4.0);
        mathPointService.create(mathPoint1);
        mathPointService.create(mathPoint2);

        mathPointService.deleteAll();
        Map<Integer, ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint> mathFuncs = mathPointService.readAll();
        assertTrue(mathFuncs.isEmpty());
    }
}
