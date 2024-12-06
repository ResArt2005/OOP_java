package SpringTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.UserRepos;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)

public class UserReposTest {

    @Autowired
    private UserRepos userRepos;

    @Test
    public void testSaveAndFindUser() {
        String login = "testLogin";
        String password = "testPassword";
        User user = new User(login, password);
        userRepos.save(user);

        Optional<User> foundUser = userRepos.findById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(login, foundUser.get().getLogin());
        assertEquals(password, foundUser.get().getPassword());
    }

    @Test
    public void testDeleteUser() {
        String login = "testLogin";
        String password = "testPassword";
        User user = new User(login, password);
        userRepos.save(user);

        userRepos.deleteById(user.getId());
        Optional<User> deletedUser = userRepos.findById(user.getId());
        assertFalse(deletedUser.isPresent());
    }
}
