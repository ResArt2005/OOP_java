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
@RequestMapping("/modal")  // Базовый путь для модальных окон
public class ModalController {
/*
    @GetMapping("/settings")
    public String getSettingsPage(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");
        model.addAttribute("isAdmin", userDTO != null && "admin".equals(userDTO.getToken()));
        return "fragments/settings :: settingsContent";  // Возвращаем фрагмент HTML
    }

    @GetMapping("/operations")
    public String getOperationsPage(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");
        model.addAttribute("isAdmin", userDTO != null && "admin".equals(userDTO.getToken()));
        return "fragments/operations :: operationsContent";  // Возвращаем фрагмент HTML
    }

    @GetMapping("/differential")
    public String getDifferentialPage(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");
        model.addAttribute("isAdmin", userDTO != null && "admin".equals(userDTO.getToken()));
        return "fragments/differential :: differentialContent";  // Возвращаем фрагмент HTML
    }

    @GetMapping("/editor")
    public String getEditorPage(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");
        model.addAttribute("isAdmin", userDTO != null && "admin".equals(userDTO.getToken()));
        return "fragments/editor :: editorContent";  // Возвращаем фрагмент HTML
    }

    @GetMapping("/integral")
    public String getIntegralPage(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");
        model.addAttribute("isAdmin", userDTO != null && "admin".equals(userDTO.getToken()));
        return "fragments/integral :: integralContent";  // Возвращаем фрагмент HTML
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");
        model.addAttribute("isAdmin", userDTO != null && "admin".equals(userDTO.getToken()));
        return "fragments/admin :: adminContent";  // Возвращаем фрагмент HTML
    }
    */
}
