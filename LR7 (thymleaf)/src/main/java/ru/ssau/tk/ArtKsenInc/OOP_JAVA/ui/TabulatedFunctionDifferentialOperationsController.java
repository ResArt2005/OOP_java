package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.ArrayTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.TBFuncDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedDifferentialOperator;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;

@Controller
//@SessionAttributes("fabricType")
@RequestMapping("/main")
public class TabulatedFunctionDifferentialOperationsController {

    private TabulatedDifferentialOperator operationService;

    @GetMapping("/differential")
    public String openOperationsWindow(Model model/*, @ModelAttribute("fabricType") TabulatedFunctionFactory fabricType*/) {
        model.addAttribute("function", new TBFuncDTO());  // пустая функция по умолчанию
        model.addAttribute("resultFunction", new TBFuncDTO());  // пустая функция для результата
        this.operationService = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());
        return "differentialOperations";
    }
    @PostMapping("/differential")
    public String differentiate(@RequestParam("function") TabulatedFunction function,
                                Model model) {
        TabulatedFunction resultFunction = operationService.derive(function);
        model.addAttribute("function", new TBFuncDTO(function));
        model.addAttribute("resultFunction", new TBFuncDTO(resultFunction));
        return "differentialOperations";
    }

    @PostMapping("/differential/insert")
    public String insertValue(@RequestParam("x") double x, @RequestParam("y") double y,
                              @ModelAttribute("function") TabulatedFunction function,
                              Model model) {
        function.insert(x, y);
        model.addAttribute("function", new TBFuncDTO(function));
        return "differentialOperations";
    }

    @PostMapping("/differential/delete")
    public String deleteValue(@RequestParam("index") int index,
                              @ModelAttribute("function") TabulatedFunction function,
                              Model model) {
        function.remove(index);
        model.addAttribute("function", new TBFuncDTO(function));
        return "differentialOperations";
    }
}
