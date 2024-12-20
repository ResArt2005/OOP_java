package SpringTests;

import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.Log;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.LogRepos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
public class LogReposTest {

    @Autowired
    private LogRepos logRepos;

    @Test
    public void testSaveAndFindLog() {
        Log log = new Log("Test message", new Timestamp(System.currentTimeMillis()));
        logRepos.save(log);

        List<Log> logs = logRepos.findByOrderByTimestampDesc();
        assertFalse(logs.isEmpty());
        assertEquals("Test message", logs.get(0).getMessage());
    }

    @Test
    public void testFindByOrderByTimestampDesc() {
        logRepos.deleteAll();
        Log log1 = new Log("Message 1", new Timestamp(System.currentTimeMillis() - 1000));
        Log log2 = new Log("Message 2", new Timestamp(System.currentTimeMillis()));
        logRepos.save(log1);
        logRepos.save(log2);

        List<Log> logs = logRepos.findByOrderByTimestampDesc();
        assertEquals(2, logs.size());
        assertEquals("Message 2", logs.get(0).getMessage());
        assertEquals("Message 1", logs.get(1).getMessage());
    }
}
