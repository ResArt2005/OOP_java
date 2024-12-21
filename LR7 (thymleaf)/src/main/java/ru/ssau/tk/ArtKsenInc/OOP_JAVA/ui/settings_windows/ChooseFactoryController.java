package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;
@Controller
@RequestMapping("/{contextPath}")
@SessionAttributes("fabricType")
public class ChooseFactoryController {
    @GetMapping("/submitFactory")
    public String getFunction(){
        return "redirect:/main";
    }
    @PostMapping("/submitFactory")
    @ResponseBody
    public void submitFunction(@RequestParam("TypeFactory") String Type, Model model) {
        TabulatedFunctionFactory factory = Type.equals("arrayFactory") ? new ArrayTabulatedFunctionFactory(): new LinkedListTabulatedFunctionFactory();
        model.addAttribute("fabricType", factory);
    }
}