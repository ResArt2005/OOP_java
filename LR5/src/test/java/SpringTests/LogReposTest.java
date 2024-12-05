package SpringTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.Log;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.LogRepos;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
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
