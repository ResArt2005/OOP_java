package SpringTests;

import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathPoint;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.MathPointRepos;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)

public class MathPointReposTest {

    @Autowired
    private MathPointRepos mathPointRepos;

    @Test
    public void testSaveAndFindMathPoint() {
        MathPoint mathPoint = new MathPoint(1.0, 2.0);
        mathPointRepos.save(mathPoint);

        Optional<MathPoint> foundMathPoint = mathPointRepos.findById(mathPoint.getId());
        assertTrue(foundMathPoint.isPresent());
        assertEquals(1.0, foundMathPoint.get().getX());
        assertEquals(2.0, foundMathPoint.get().getY());
    }

    @Test
    public void testDeleteMathPoint() {
        MathPoint mathPoint = new MathPoint(1.0, 2.0);
        mathPointRepos.save(mathPoint);

        mathPointRepos.deleteById(mathPoint.getId());
        Optional<MathPoint> deletedMathPoint = mathPointRepos.findById(mathPoint.getId());
        assertFalse(deletedMathPoint.isPresent());
    }
}
