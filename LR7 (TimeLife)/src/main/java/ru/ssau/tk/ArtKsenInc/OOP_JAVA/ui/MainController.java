package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.UserDTO;

@SessionAttributes("userDTO")
@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");

        // Добавляем информацию о пользователе для проверки на права админа
        model.addAttribute("isAdmin", userDTO != null && "admin".equals(userDTO.getToken()));

        return "main";  // Возвращаем имя шаблона (main.html)
    }

    @GetMapping("/settings")
    public String openSettingsPage() {
        return "settings";  // Создайте аналогичный settings.html шаблон
    }

    @GetMapping("/operations")
    public String openOperationsPage() {
        return "operations";  // Аналогично для operations.html
    }

    @GetMapping("/differential")
    public String openDifferentialPage() {
        return "differential";  // Аналогично для differential.html
    }

    @GetMapping("/editor")
    public String openEditorPage() {
        return "editor";  // Аналогично для editor.html
    }

    @GetMapping("/integral")
    public String openIntegralPage() {
        return "integral";  // Аналогично для integral.html
    }

    @GetMapping("/admin")
    public String openAdminPage() {
        return "admin";  // Аналогично для admin.html
    }
}
