package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String showRegistrationForm(HttpSession session, Model model) {
        String header = "Привет html";
        model.addAttribute("header", header);
        model.addAttribute("newHeader", session.getAttribute("newHeader")); // Получаем значение из сессии
        return "main"; // Возвращает имя Thymeleaf шаблона
    }

    @PostMapping("/")
    public String Authorisation(
            HttpSession session, @RequestParam("text") String text) {
        session.setAttribute("newHeader", text);
        return "redirect:/";// Перенаправляем на главную страницу
    }
}
