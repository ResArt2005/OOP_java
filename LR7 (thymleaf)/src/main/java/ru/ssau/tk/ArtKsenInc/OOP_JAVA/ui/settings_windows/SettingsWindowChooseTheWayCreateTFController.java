package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.TabulatedFunctionByMathFunctionWindow;
//import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.TabulatedFunctionByArraysWindow;

@Controller
@SessionAttributes("tabulatedFunction")  // Сессия для хранения функции
public class SettingsWindowChooseTheWayCreateTFController {

    private TabulatedFunction function;
    /*private final TabulatedFunctionOperationService factoryService;

    public SettingsWindowChooseTheWayCreateTFController(TabulatedFunctionOperationService factoryService) {
        this.factoryService = factoryService;
    }*/

    @GetMapping("/chooseFunctionCreationMethod")
    public String chooseFunctionCreationMethod(Model model) {
        // Отображение страницы выбора метода создания функции
        return "chooseFunctionCreationMethod"; // Это страница, где выбираются способы создания функции
    }

    @GetMapping("/openTabulatedFunctionByArraysWindow")
    public String openTabulatedFunctionByArraysWindow(Model model) {
        // Создание окна для выбора метода по массивам
        //TabulatedFunctionByArraysWindow arraysWindow = new TabulatedFunctionByArraysWindow(factoryService.getFactory());
        //function = arraysWindow.getTabulatedFunction();  // Получаем функцию
        model.addAttribute("tabulatedFunction", function);
        return "functionCreatedPage";  // Страница с созданной функцией
    }

    @GetMapping("/openTabulatedFunctionByMathFunctionWindow")
    public String openTabulatedFunctionByMathFunctionWindow(Model model) {
        // Создание окна для выбора метода по математической функции
        //TabulatedFunctionByMathFunctionWindow mathWindow = new TabulatedFunctionByMathFunctionWindow(factoryService.getFactory());
        //function = mathWindow.getTabulatedFunction();  // Получаем функцию
        model.addAttribute("tabulatedFunction", function);
        return "functionCreatedPage";  // Страница с созданной функцией
    }

    @ModelAttribute("tabulatedFunction")
    public TabulatedFunction getTabulatedFunction() {
        return function;  // Возвращаем созданную функцию
    }
}
