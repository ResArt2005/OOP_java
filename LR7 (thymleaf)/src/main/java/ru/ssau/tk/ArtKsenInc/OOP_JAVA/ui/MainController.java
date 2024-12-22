package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.UserDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionScanner;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;


import java.util.Map;

@SessionAttributes({"userDTO", "fabricType"})
@Controller
public class MainController {
    @GetMapping("/initSession")
    public String init(HttpSession session, Model model){
        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("fabricType");
        if(factory == null){
            model.addAttribute("fabricType", new ArrayTabulatedFunctionFactory());
        }
        return "redirect:/main";
    }
    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model) {
        // Проверяем, является ли пользователь администратором
        UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");
        if (userDTO == null) {
            return "redirect:/";
        }
        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("fabricType");
        if(factory instanceof ArrayTabulatedFunctionFactory){
            model.addAttribute("FactoryRadio", "arrayFactory");
        }
        else{
            model.addAttribute("FactoryRadio", "linkedListFactory");
        }
        Map<String, MathFunction> functionMap = MathFunctionScanner.getAnnotatedFunctions(); // Используем динамическое сканирование
        functionMap.putAll(dbTools.getAllMathFunctionsAsNameAndMF());
        model.addAttribute("functions", functionMap.keySet());
        model.addAttribute("isAdmin", "admin".equals(userDTO.getToken()));
        return "main";
    }
}
