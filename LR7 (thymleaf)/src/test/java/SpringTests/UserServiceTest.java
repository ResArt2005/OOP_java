package SpringTests;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.repository.UserRepos;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.service.UserService;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepos userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    //Чистит таблицу
    //@AfterEach
    //public void tearDown() {
    //    userRepository.deleteAll();
    //}

    @Test
    public void testCreate() {
        User user = new User("testLogin", "testPassword");
        User createdUser = userService.create(user);
        assertNotNull(createdUser.getId());
        assertEquals("testLogin", createdUser.getLogin());
        assertEquals("testPassword", createdUser.getPassword());
    }

    @Test
    public void testReadAll() {
        User user1 = new User();
        User user2 = new User();
        userService.create(user1);
        userService.create(user2);
        Map<String, User> result = userService.readAll();

        assertEquals(2, result.size());
    }

    @Test
    public void testUpdate() {
        User existingUser = new User("oldLogin", "oldPassword");
        int id = userService.create(existingUser).getId();
        User updatedUser = new User("newLogin", "newPassword");
        User result = userService.update(id, updatedUser);

        assertEquals("newLogin", result.getLogin());
        assertEquals("newPassword", result.getPassword());
    }

    @Test
    public void testGetById_UserFound() {
        User user = new User();
        int id = userService.create(user).getId();
        User foundUser = userService.getById(id);

        assertNotNull(foundUser);
        assertEquals(id, foundUser.getId());
    }

    @Test
    public void testGetById_UserNotFound() {
        userRepository.deleteAll();
        Map<String, User> users = userService.readAll();

        assertEquals(0, users.size());
    }

    @Test
    public void testDeleteById() {
        User user = new User();
        user.setId(1);
        userService.create(user);
        userService.deleteById(1);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> userService.getById(1));

        assertEquals("User with id 1 not found", exception.getMessage());
    }

    @Test
    public void testDeleteAll() {
        userRepository.deleteAll();
        User user = new User();
        user.setId(1);
        userService.create(user);
        userService.deleteAll();
        Exception exception = assertThrows(EntityNotFoundException.class, () -> userService.getById(1));

        assertEquals("User with id 1 not found", exception.getMessage());
    }
}
