package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionScanner;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import java.util.Map;

@Controller
@RequestMapping("/{contextPath}")
@SessionAttributes("fabricType")
public class TabulatedFunctionByMathFunctionController {
    @GetMapping("/createFunctionByFunction")
    public String returnToMain() {
        return "redirect:/main";
    }

    @PostMapping("/submitFunctionByFunction")
    @ResponseBody
    public void submitFunction(@RequestParam("funcName") String funcName,
                               @RequestParam("leftBound") double leftBound,
                               @RequestParam("rightBound") double rightBound,
                               @RequestParam("pointCount") int pointCount,
                               HttpSession session) {
        double[] xValues = new double[pointCount];
        double[] yValues = new double[pointCount];
        double step = (rightBound - leftBound) / (pointCount - 1);
        Map<String, MathFunction> functionMap;
        functionMap = MathFunctionScanner.getAnnotatedFunctions();
        functionMap.putAll(dbTools.getAllMathFunctionsAsNameAndMF());
        MathFunction selectedFunction = functionMap.get(funcName);
        for (int i = 0; i < pointCount; i++) {
            xValues[i] = leftBound + i * step;
            yValues[i] = selectedFunction.apply(xValues[i]);
        }
        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("fabricType");
        TabulatedFunction function = factory.create(xValues, yValues);
    }
}