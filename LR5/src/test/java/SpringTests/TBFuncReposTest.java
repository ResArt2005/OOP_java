package SpringTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.TBFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.TBFuncRepos;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)

public class TBFuncReposTest {

    @Autowired
    private TBFuncRepos tbFuncRepos;

    @Test
    public void testSaveAndFindTBFunc() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        TBFunc tbFunc = new TBFunc(xValues, yValues);
        tbFuncRepos.save(tbFunc);

        Optional<TBFunc> foundTBFunc = tbFuncRepos.findById(tbFunc.getId());
        assertTrue(foundTBFunc.isPresent());
        assertArrayEquals(xValues, foundTBFunc.get().getXValues());
        assertArrayEquals(yValues, foundTBFunc.get().getYValues());
    }

    @Test
    public void testDeleteTBFunc() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        TBFunc tbFunc = new TBFunc(xValues, yValues);
        tbFuncRepos.save(tbFunc);

        tbFuncRepos.deleteById(tbFunc.getId());
        Optional<TBFunc> deletedTBFunc = tbFuncRepos.findById(tbFunc.getId());
        assertFalse(deletedTBFunc.isPresent());
    }
}
