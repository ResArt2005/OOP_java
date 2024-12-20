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
@SessionAttributes("userDTO")
@Controller
public class ChooseUserController {
    private User newUser;
    @GetMapping("/")
    public String showRegistrationForm(Model model) {
        dbTools.addAdmin();
        newUser = new User();
        model.addAttribute("token", newUser.getToken()); // Передаем токен в шаблон
        return "registration"; // Возвращает имя Thymeleaf шаблона
    }

    @PostMapping("/")
    public String Authorisation(
            @RequestParam("token") String token,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        // Проверяем поля на пустоту
        if (token.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Логин и пароль не могут быть пустыми");
            return "registration";
        }

        // Получаем пользователя по токену
        User user = dbTools.getUserByToken(token);

        // Проверяем, что пользователь существует
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Неверный токен или пароль");
            return "registration";
        }
        // Логируем успешный вход пользователя
        dbTools.createLog(user.getLogin() + " вошёл в систему");

        // Создаем объект UserDTO и сохраняем его в сессии
        model.addAttribute("userDTO", new UserDTO(user));

        model.addAttribute("success", "Успешный вход");

        return "redirect:/main";
    }

    @GetMapping("/register")
    public String getRegistrationToken(Model model) {
        //newUser = new User();
        //model.addAttribute("token", newUser.getToken()); // Передаем токен в шаблон
        return "registration"; // Возвращаем ту же страницу с модальным окном
    }

    // Обработка данных регистрации
    @PostMapping("/register")
    public String registerUser(
            @RequestParam("login") String username,
            @RequestParam("password_2") String password,
            Model model) {
        if(newUser == null){
            return "registration";
        }
        if (username.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Все поля обязательны для заполнения!");
            model.addAttribute("token", newUser.getToken()); // Передаем токен обратно в форму
            return "registration"; // Оставляем пользователя на той же странице
        }
        newUser.setLogin(username);
        newUser.setPassword(password);
        dbTools.createUser(newUser);
        dbTools.createLog(username + " добавился в систему");
        // Перенаправление на главную страницу после успешной регистрации
        return "redirect:/";
    }
}
