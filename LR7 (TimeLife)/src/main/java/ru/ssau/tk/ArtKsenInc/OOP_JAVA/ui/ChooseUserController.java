package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.UserDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import java.util.Objects;

@SessionAttributes("userDTO")
@Controller
public class ChooseUserController {

    @GetMapping("/")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Пустая модель пользователя для формы
        return "registration"; // Возвращает имя Thymeleaf шаблона
    }

    @PostMapping("/")
    public String Authorisation(
            @RequestParam("token") String token,
            @RequestParam("password") String password,
            HttpSession session, // Добавьте HttpSession
            Model model) {

        // Проверяем поля на пустоту
        if (token.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Логин и пароль не могут быть пустыми");
            return "registration";
        }

        // Создаем нового пользователя
        User user = dbTools.getUserByToken(token);
        System.out.println(user.getLogin() + " " + user.getPassword());
        if(user == null || !user.getPassword().equals(password)){
            model.addAttribute("error", "Неверный токен или пароль");
            return "registration";
        }
        dbTools.createLog(user.getLogin() + " вошёл в систему");

        // Создаем объект UserDTO и сохраняем его в сессии
        session.setAttribute("userDTO", new UserDTO(user)); // Сохраняем объект в сессии

        // Добавляем сообщение об успешной регистрации
        model.addAttribute("success", "Успешный вход");

        return "redirect:/main"; // Перенаправляем на главную страницу
    }
    @PostMapping("/register")
    public String registerRegistration(
            @RequestParam("token") String login,
            @RequestParam("password") String password,
            Model model) {
        User newUser = new User("", "");
        model.addAttribute("tokenField", newUser.getToken());
        // Проверяем поля на пустоту
        if (login.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Логин и пароль не могут быть пустыми");
            return "registration";
        }

        // Создаем нового пользователя
        newUser.setLogin(login);
        newUser.setPassword(password);
        dbTools.createUser(newUser);
        dbTools.createLog(newUser.getLogin() + " добавился в систему");

        // Добавляем сообщение об успешной регистрации
        model.addAttribute("success", "Новый пользователь успешно создан!");

        return "registration"; // Перенаправляем на главную страницу
    }
}
