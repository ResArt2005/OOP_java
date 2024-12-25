package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.CompositeFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.XFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.UserDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.MathFunc;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionScanner;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;


import java.sql.SQLDataException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SessionAttributes({"userDTO", "fabricType", "newName", "pointCount"})
@Controller
public class MainController {
    @GetMapping("/initSession")
    public String init(HttpSession session, Model model) {
        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("fabricType");
        if (factory == null) {
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
        if (factory instanceof ArrayTabulatedFunctionFactory) {
            model.addAttribute("FactoryRadio", "arrayFactory");
        } else {
            model.addAttribute("FactoryRadio", "linkedListFactory");
        }
        Map<String, MathFunction> functionMap = MathFunctionScanner.getAnnotatedFunctions(); // Используем динамическое сканирование
        functionMap.putAll(dbTools.getAllMathFunctionsAsNameAndMF());
        model.addAttribute("functions", functionMap.keySet());
        model.addAttribute("isAdmin", "admin".equals(userDTO.getToken()));
        return "main";
    }

    //{ String funcNames, ... }, int countOfFunctions, String newName
    @PostMapping("/hardFunction")
    @ResponseBody
    public String hardFunction(@RequestParam("newName") String newName, @RequestParam("pointCount") int pointCount, Model model) {
        if (newName == null || newName.isEmpty()) {
            return "Введите корректное имя";
        }
        /*MathFunc mathFunc = new MathFunc(newName, new XFunction());
        try {
            dbTools.createMathFunction(mathFunc);
        } catch (DataIntegrityViolationException e) {
            dbTools.deleteTBFunctionById(mathFunc.getId());
            return "Такое имя уже существует, придумайте другое";
        } catch (IllegalArgumentException e) {
            dbTools.deleteTBFunctionById(mathFunc.getId());
            return e.toString();
        }
        dbTools.deleteTBFunctionById(mathFunc.getId());*/
        model.addAttribute("newName", newName);
        model.addAttribute("pointCount", pointCount);
        return createList(pointCount);
    }

    @PostMapping("/hardFunctionSubmit")
    public ResponseEntity<Map<String, String>> hardFunctionSubmit(@RequestBody List<String> funcNames, HttpSession session) {
        int pointCount = (int) session.getAttribute("pointCount");
        String newName = (String) session.getAttribute("newName");
        MathFunction[] selectedFunctions = new MathFunction[pointCount];
        Map<String, MathFunction> functionMap = MathFunctionScanner.getAnnotatedFunctions();
        functionMap.putAll(dbTools.getAllMathFunctionsAsNameAndMF());
        int j = 0;
        for (String funcName : funcNames) {
            selectedFunctions[j] = functionMap.get(funcName);
            ++j;
        }
        // Создаём сложную функцию
        MathFunction composite = selectedFunctions[0];
        for (int i = 1; i < pointCount - 1; i++) {
            composite = composite.andThen(selectedFunctions[i]);
        }
        CompositeFunction compositeFunction = new CompositeFunction(composite, selectedFunctions[pointCount - 1]);
        dbTools.createMathFunction(newName, compositeFunction);
        dbTools.createLog("Создана математическая функция " + newName);
        Map<String, String> response = new HashMap<>();
        response.put("message", "success");
        return ResponseEntity.ok(response);
    }

    private String createList(int countOfFunctions) {
        Map<String, MathFunction> functionMap = MathFunctionScanner.getAnnotatedFunctions();
        functionMap.putAll(dbTools.getAllMathFunctionsAsNameAndMF());
        StringBuilder sb = new StringBuilder();
        sb.append("<button id='submitCreationMethFunction' data-modal-id='modalMathFunc' type='button' class='art_byArr_create-function-btn'>Создать функцию</button>");
        for (int i = 0; i < countOfFunctions; i++) {
            sb.append("<div class=\"art_dropdown\">");
            sb.append("<button class=\"createFunc art_dropdown-button\">Выберите функцию</button>");
            sb.append("<div class=\"art_dropdown-content\">");
            sb.append("<ul class=\"art_dropdown-list\">");
            for (String str : functionMap.keySet()) {
                sb.append("<li style='cursor:pointer;'>");  // Добавлено закрытие угла
                sb.append("<a th:href=\"'#'\" class=\"art_dropdown-item\">").append(str).append("</a>");
                sb.append("</li>");  // Закрывается тег <li>
            }
            sb.append("</ul>");
            sb.append("</div>");
            sb.append("</div>");
        }
        return sb.toString();
    }
}
