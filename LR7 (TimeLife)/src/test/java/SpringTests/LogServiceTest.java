package SpringTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.Log;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.LogRepos;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service.LogService;
import java.sql.Timestamp;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)

public class LogServiceTest {

    @Autowired
    private LogService logService;

    @Autowired
    private LogRepos logRepository;

    @BeforeEach
    public void setUp() {
        logRepository.deleteAll();
    }
    @AfterEach
    public void tearDown() {
        logRepository.deleteAll();
    }
    @Test
    public void testCreate() {
        Log log = new Log("Test log", new Timestamp(System.currentTimeMillis()));
        Log createdLog = logService.create(log);
        assertNotNull(createdLog.getId());
        assertEquals("Test log", createdLog.getMessage());
    }

    @Test
    public void testReadAll() {
        Log log1 = new Log("Log 1", new Timestamp(System.currentTimeMillis()));
        Log log2 = new Log("Log 2", new Timestamp(System.currentTimeMillis()));
        logService.create(log1);
        logService.create(log2);

        List<Log> logs = logService.readAll();
        assertEquals(2, logs.size());
    }

    @Test
    public void testFindByOrderByTimestampDesc() {
        Log log1 = new Log("Log 1", new Timestamp(System.currentTimeMillis()));
        Log log2 = new Log("Log 2", new Timestamp(System.currentTimeMillis() + 1000));
        logService.create(log1);
        logService.create(log2);

        List<Log> orderedLogs = logService.findByOrderByTimestampDesc();
        assertEquals("Log 2", orderedLogs.get(0).getMessage());
    }

    @Test
    public void testUpdate() {
        Log log = new Log("Test log", new Timestamp(System.currentTimeMillis()));
        Log createdLog = logService.create(log);

        createdLog.setMessage("Updated log");
        Log updatedLog = logService.update(createdLog);
        assertEquals("Updated log", updatedLog.getMessage());
    }

    @Test
    public void testDeleteById() {
        Log log = new Log("Test log", new Timestamp(System.currentTimeMillis()));
        Log createdLog = logService.create(log);

        logService.deleteById(createdLog.getId());
        List<Log> logs = logService.readAll();
        assertTrue(logs.isEmpty());
    }

    @Test
    public void testDeleteAll() {
        Log log1 = new Log("Log 1", new Timestamp(System.currentTimeMillis()));
        Log log2 = new Log("Log 2", new Timestamp(System.currentTimeMillis()));
        logService.create(log1);
        logService.create(log2);

        logService.deleteAll();
        List<Log> logs = logService.readAll();
        assertTrue(logs.isEmpty());
    }
}
